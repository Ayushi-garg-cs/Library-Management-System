package com.project.service;

import com.project.exception.UserException;
import com.project.payload.dto.UserDto;
import com.project.payload.response.AuthResponse;

public interface AuthService {
    AuthResponse login(String username, String password);
    AuthResponse register(UserDto user) throws UserException;

    void createPasswordResetToken(String email);
    void resetPassword(String token, String newPassword);

}
