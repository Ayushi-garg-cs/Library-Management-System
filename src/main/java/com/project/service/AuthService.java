package com.project.service;

import com.project.exception.UserException;
import com.project.payload.dto.UserDto;
import com.project.payload.response.AuthResponse;

public interface AuthService {
    AuthResponse login(String username, String password) throws UserException;
    AuthResponse register(UserDto user) throws UserException;

    void createPasswordResetToken(String email) throws UserException;
    void resetPassword(String token, String newPassword);

}
