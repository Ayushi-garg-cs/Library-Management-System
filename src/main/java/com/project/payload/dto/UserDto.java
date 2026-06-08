package com.project.payload.dto;

import com.project.domain.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String email;
    private String fullName;
    private UserRole role;
    private String password;
    private String phone;
    private LocalDateTime lastLogin;
}
