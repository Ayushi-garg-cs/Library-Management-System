package com.project.repository;

import com.project.domain.UserRole;
import com.project.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);
    Set<User> findByRole(UserRole role);
}
