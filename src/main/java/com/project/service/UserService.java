package com.project.service;

import com.project.exception.UserException;
import com.project.payload.dto.UserDto;

import java.util.List;

public interface UserService {
    public UserDto getcurrentUser() throws UserException;
    public List<UserDto> getAllUsers();
}
