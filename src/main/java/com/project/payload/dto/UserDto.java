package com.project.payload.dto;

import com.project.domain.UserRole;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.bridge.IMessage;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    @NotNull(message="Email is required")
    private String email;
    @NotNull(message="FullName is required")
    private String fullName;
    private UserRole role;
    @NotNull(message="Password is required")
    private String password;
    private String phone;
    private LocalDateTime lastLogin;
}
