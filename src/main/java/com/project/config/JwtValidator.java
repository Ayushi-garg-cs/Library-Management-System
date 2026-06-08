package com.project.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.security.Key;
import java.util.List;

public class JwtValidator extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String jwt=request.getHeader(JwtConstant.JWT_HEADER);
        //Bearer wjhwakjjhj(remove Bearer keyword from jwt token)
        if(jwt!=null){
            //means skip 7 starting letters of jwt
            //means Bearer abc.xyz.123->abc.xyz.123
            jwt=jwt.substring(7);

            try{
                SecretKey key= Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
                //it will verify the jwt token and throw error if not verified
                Claims claims= Jwts.parser().verifyWith(key).build()
                        .parseSignedClaims(jwt).getPayload();

                String email=String.valueOf(claims.get("email"));
                // this authorities give role of user having upper email
                String authorities=String.valueOf(claims.get("authorities"));
                //role will come in form of string so convert into list
                List<GrantedAuthority> authoritiesList= AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
                Authentication authentication=new UsernamePasswordAuthenticationToken(email,null,authoritiesList);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }catch(Exception e){
                throw new BadCredentialsException("Invalid JWT token");
            }
        }
        filterChain.doFilter(request,response);
    }
}
