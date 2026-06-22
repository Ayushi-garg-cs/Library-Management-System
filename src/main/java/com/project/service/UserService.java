package com.project.service;

import com.project.domain.UserRole;
import com.project.exception.UserException;
import com.project.modal.User;
import com.project.payload.dto.UserDto;

import java.util.List;
import java.util.Set;

public interface UserService {
    User getUserByEmail(String email) throws UserException;
    User getUserFromJwtToken(String jwt) throws UserException;
    User getUserById(Long id) throws UserException;
    Set<User> getUserByRole(UserRole role) throws UserException;
    List<User> getUsers() throws UserException;
    public UserDto getcurrentUser() throws UserException;
    public List<UserDto> getAllUsers();

//    Get total count of all registered users (Admin only)
    long getTotalUserCount();
}
