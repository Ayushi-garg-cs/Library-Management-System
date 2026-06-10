package com.project.service.impl;

import com.project.domain.UserRole;
import com.project.exception.UserException;
import com.project.modal.User;
import com.project.payload.dto.UserDto;
import com.project.payload.response.AuthResponse;
import com.project.repository.UserRepository;
import com.project.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse login(String username, String password) {
        return null;
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

        Authentication auth=new UsernamePasswordAuthenticationToken(savedUser.getEmail(),null,authoritiesList);
        SecurityContextHolder.getContext().setAuthentication(auth);
        return null;
    }

    @Override
    public void createPasswordResetToken(String email) {

    }

    @Override
    public void resetPassword(String token, String newPassword) {

    }
}
