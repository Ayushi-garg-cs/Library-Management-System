package com.project.config;

import io.jsonwebtoken.JwtVisitor;
import jakarta.servlet.http.HttpServletRequest;
import org.jspecify.annotations.Nullable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .sessionManagement(management->management.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS
                ))
                .authorizeHttpRequests(Authorize->Authorize
                        .requestMatchers("/api/**").authenticated()
                        //adding more security
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .anyRequest().permitAll()
                )
                .addFilterBefore(new JwtValidator(), BasicAuthenticationFilter.class)
                //because we are using JWT Token there is no need of csrf now
                .csrf(AbstractHttpConfigurer::disable)
                //cors configuration so inside this we will tell backend that this is my frontend url ...if somebody wants to fetch data from this url then give response and don't block request
                .cors(cors->cors.configurationSource(corsConfigurationSource()))
                .build();

    }

    private CorsConfigurationSource corsConfigurationSource() {
        return new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration corsConfiguration = new CorsConfiguration();
                corsConfiguration.setAllowCredentials(true);
                //this * means all frontend url can fetch data from backend but we cant provide this star here...we need to provide only our frontend domain
                //corsConfiguration.addAllowedOrigin("*");
                corsConfiguration.setAllowedOrigins(
                        //provide url of frontend...ispe hi to run karta ha react ka frontend
                        Arrays.asList(
                                "http://localhost:5173/",
                                "https://readify.com"
                        )
                );
                //how many methods do you want to allow for this(GET,PUT,POST,DELETE)
                corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
                corsConfiguration.setExposedHeaders(Collections.singletonList("Authorization"));
                corsConfiguration.setMaxAge(360L);
                return corsConfiguration;
            }
        };
    }

    //so whatever class will start from class API it should be authenticated ....without JWT token it won't be accessed
}
