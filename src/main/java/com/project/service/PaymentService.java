package com.project.service;

import com.project.exception.PaymentException;
import com.project.payload.dto.PaymentDto;
import com.project.payload.request.PaymentInitiateRequest;
import com.project.payload.request.PaymentVerifyRequest;
import com.project.payload.response.PaymentInitiateResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaymentService {
    PaymentInitiateResponse initiatePayment(PaymentInitiateRequest req) throws PaymentException;
    PaymentDto verifyPayment(PaymentVerifyRequest req);
    PaymentDto getPaymentById(Long paymentId) throws PaymentException;

    /**
     * Get payment by transaction ID
     */
    PaymentDto getPaymentByTransactionId(String transactionId) throws PaymentException;

    /**
     * Get all payments for a user
     */
    Page<PaymentDto> getUserPayments(Long userId, Pageable pageable) throws PaymentException;

    /**
     * Get all payments (admin)
     */
    Page<PaymentDto>  getAllPayments(Pageable pageable);

    /**
     * Cancel a pending payment
     */
    PaymentDto cancelPayment(Long paymentId) throws PaymentException;

    /**
     * Retry a failed payment
     */
    PaymentInitiateResponse retryPayment(Long paymentId) throws PaymentException;

    /**
     * Get monthly revenue statistics (Admin only)
     */
//    RevenueStatisticsResponse getMonthlyRevenue();
}
