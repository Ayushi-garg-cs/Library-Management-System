package com.project.controller;

import com.project.exception.UserException;
import com.project.payload.dto.UserDto;
import com.project.payload.request.ForgotPasswordRequest;
import com.project.payload.request.LoginRequest;
import com.project.payload.request.ResetPasswordRequest;
import com.project.payload.response.ApiResponse;
import com.project.payload.response.AuthResponse;
import com.project.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signUpHandler(@RequestBody @Valid UserDto userDto) throws Exception, UserException {
        AuthResponse res=authService.register(userDto);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginHandler(@RequestBody @Valid LoginRequest req) throws Exception, UserException {
        AuthResponse res=authService.login(req.getEmail(), req.getPassword());
        return ResponseEntity.ok(res);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse> forgotPasswordHandler(@RequestBody ForgotPasswordRequest req) throws UserException {
        authService.createPasswordResetToken(req.getEmail());
        ApiResponse res=new ApiResponse("A Reset Link is sent to your email",true);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse> resetPasswordHandler(@RequestBody @Valid ResetPasswordRequest req) throws Exception {
        authService.resetPassword(req.getToken(), req.getNewPassword());
        ApiResponse res=new ApiResponse("Password reset successfully",true);
        return  ResponseEntity.ok(res);
    }
}
