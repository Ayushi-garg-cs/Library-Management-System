package com.project.repository;

import com.project.modal.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken,Long> {

    <Optional>PasswordResetToken findByToken(String token);
}
