package com.project.service.impl;

import com.project.config.JwtProvider;
import com.project.domain.UserRole;
import com.project.exception.UserException;
import com.project.mapper.UserMapper;
import com.project.modal.PasswordResetToken;
import com.project.modal.User;
import com.project.payload.dto.UserDto;
import com.project.payload.response.AuthResponse;
import com.project.repository.PasswordResetTokenRepository;
import com.project.repository.UserRepository;
import com.project.service.AuthService;
import com.project.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final UserMapper userMapper;
    private final CustomUserDetailServiceImpl customUserDetailService;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final EmailService emailService;

    @Override
    public AuthResponse login(String username, String password) throws UserException {
        Authentication authentication=authenticate(username,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

//        Collection<?  extends GrantedAuthority> authorities = authentication.getAuthorities();
//        String role=authorities.iterator().next().getAuthority();
        String token= jwtProvider.generateAccessToken(authentication);

        //update last login
        User user=userRepository.findByEmail(username);
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        AuthResponse authResponse=new  AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Successfully Logged In");
        authResponse.setTitle("Welcome Back:"+username);
        authResponse.setUser(userMapper.toDto(user));

        return authResponse;
    }

    private Authentication authenticate(String username, String password) throws UserException {
        UserDetails  userDetails=customUserDetailService.loadUserByUsername(username);

        if(userDetails==null){
            throw new UsernameNotFoundException("User not found");
        }
        //compare login vala password and database vala password
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new UserException("Password not matched");
        }
        return new  UsernamePasswordAuthenticationToken(userDetails,password,userDetails.getAuthorities());
    }

    @Override
    public AuthResponse register(UserDto userDto) throws UserException {
        User user=userRepository.findByEmail(userDto.getEmail());
        if(user==null){
            throw new UserException("email id already registered");
        }
        User createdUser=new User();
        createdUser.setEmail(userDto.getEmail());
        createdUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        createdUser.setPhone(userDto.getPhone());
        createdUser.setFullName(userDto.getFullName());
        createdUser.setRole(userDto.getRole());
        createdUser.setLastLogin(LocalDateTime.now());
        createdUser.setRole(UserRole.ROLE_USER);
        User savedUser=userRepository.save(createdUser);

        UsernamePasswordAuthenticationToken auth=new UsernamePasswordAuthenticationToken(savedUser.getEmail(),savedUser.getPassword());
        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwt=jwtProvider.generateAccessToken(auth);
        AuthResponse authResponse=new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setTitle("Welcome:"+createdUser.getFullName());
        authResponse.setMessage("Successfully registered");
        authResponse.setUser(UserMapper.toDto(savedUser));

        return authResponse;
    }

    //to reset password
    @Override
    public void createPasswordResetToken(String email) throws UserException {

        String frontendUrl="";

        User user=userRepository.findByEmail(email);
        if(user==null){
            throw new UserException("User not found with given email");
        }

        String token= UUID.randomUUID().toString();
        //we need to create a db for passwordResetToken
        PasswordResetToken resetToken= PasswordResetToken.builder()
                .token(token)
                .user(user)
                .expiryDate(LocalDateTime.now().plusMinutes(5))
                .build();
        passwordResetTokenRepository.save(resetToken);
        //make frontendUrl at starting of this function
        String resetLink=frontendUrl+token;
        String subject="Password Reset Request";
        String body="You requested to reset your password. Use this Link (valid 5 minutes):"+resetLink;

        //sent email
        //make email service
        emailService.sendEmail(user.getEmail(),subject,body);

    }

    @Override
    public void resetPassword(String token, String newPassword) {





    }
}
