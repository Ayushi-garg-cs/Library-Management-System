package com.project.repository;

import com.project.modal.Payment;
import com.project.payload.dto.PaymentDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
