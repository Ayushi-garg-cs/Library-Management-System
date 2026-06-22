package com.project.service.impl;

import com.project.config.JwtProvider;
import com.project.domain.UserRole;
import com.project.exception.UserException;
import com.project.mapper.UserMapper;
import com.project.modal.User;
import com.project.payload.dto.UserDto;
import com.project.repository.UserRepository;
import com.project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    //during login in authserviceimpl we have stored user's token in securityContext
    @Override
    public UserDto getcurrentUser() throws UserException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user=userRepository.findByEmail(email);
        if(user==null){
            throw new UserException("User not found");
        }
        return UserMapper.toDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users=userRepository.findAll();

        return users.stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public User getUserByEmail(String email) throws UserException {
        User user=userRepository.findByEmail(email);
        if(user==null){
            throw new UserException("User not found with email: "+email);
        }
        return user;
    }

    @Override
    public User getUserFromJwtToken(String jwt) throws UserException {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        User user = userRepository.findByEmail(email);
        if(user==null) throw new UserException("user not exist with email "+email);
        return user;
    }

    @Override
    public User getUserById(Long id) throws UserException {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public Set<User> getUserByRole(UserRole role) throws UserException {
        return userRepository.findByRole(role);
    }

    @Override
    public List<User> getUsers() throws UserException {
        return userRepository.findAll();
    }

    @Override
    public long getTotalUserCount() {
        return userRepository.count();
    }


}
