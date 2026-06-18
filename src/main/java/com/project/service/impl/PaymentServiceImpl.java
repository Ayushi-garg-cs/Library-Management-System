package com.project.service.impl;

import com.project.domain.PaymentGateway;
import com.project.domain.PaymentStatus;
import com.project.exception.PaymentException;
import com.project.modal.Payment;
import com.project.modal.Subscription;
import com.project.modal.User;
import com.project.payload.dto.PaymentDto;
import com.project.payload.request.PaymentInitiateRequest;
import com.project.payload.request.PaymentVerifyRequest;
import com.project.payload.response.PaymentInitiateResponse;
import com.project.payload.response.PaymentLinkResponse;
import com.project.repository.PaymentRepository;
import com.project.repository.SubscriptionRepository;
import com.project.repository.UserRepository;
import com.project.service.PaymentService;
import com.project.service.gateway.RazorPayService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final PaymentRepository paymentRepository;
    private final RazorPayService razorPayService;

    @Override
    public PaymentInitiateResponse initiatePayment(PaymentInitiateRequest req) throws PaymentException {
        User user=userRepository.findById(req.getUserId()).get();

        Payment payment = new Payment();
        payment.setUser(user);
        payment.setPaymentType(req.getPaymentType());
        payment.setGateway(req.getGateway());
        payment.setAmount(req.getAmount());
        payment.setCurrency(req.getCurrency() != null ? req.getCurrency() : "INR");
        payment.setDescription(req.getDescription());
        payment.setStatus(PaymentStatus.PENDING);
        payment.setTransactionId("TXN_" + UUID.randomUUID());
        payment.setInitiatedAt(LocalDateTime.now());

        //link subscription
        if (req .getSubscriptionId() != null) {
            Subscription sub = subscriptionRepository
                    .findById(req.getSubscriptionId())
                    .orElseThrow(() -> new PaymentException("Subscription not found"));
            payment.setSubscription(sub);
        }

        payment=paymentRepository.save(payment);


        // 4. Initiate payment with gateway
        PaymentInitiateResponse response = null;
        if (req.getGateway() == PaymentGateway.RAZORPAY) {
            PaymentLinkResponse linkResponse = razorPayService.createPaymentLink(
                    user,
                    payment
            );

            response = PaymentInitiateResponse.builder()
                    .paymentId(payment.getId())
                    .gateway(payment.getGateway())
                    .checkoutUrl(linkResponse.getPayment_link_url())
                    .transactionId(linkResponse.getPayment_link_id())
                    .amount(payment.getAmount())
                    .currency(payment.getCurrency())
                    .description(payment.getDescription())
                    .success(true)
                    .message("Payment initiated successfully")
                    .build();
            payment.setGatewayOrderId(linkResponse.getPayment_link_id());
        }
        payment.setStatus(PaymentStatus.PROCESSING);
        payment = paymentRepository.save(payment);

        // Publish payment initiated event
        //publishPaymentInitiatedEvent(payment, response.getCheckoutUrl());

        return response;
    }

    @Override
    public PaymentDto verifyPayment(PaymentVerifyRequest req) {
        return null;
    }

    @Override
    public PaymentDto getPaymentById(Long paymentId) throws PaymentException {
        return null;
    }

    @Override
    public PaymentDto getPaymentByTransactionId(String transactionId) throws PaymentException {
        return null;
    }

    @Override
    public Page<PaymentDto> getUserPayments(Long userId, Pageable pageable) throws PaymentException {
        return null;
    }

    @Override
    public Page<PaymentDto> getAllPayments(Pageable pageable) {
        return null;
    }

    @Override
    public PaymentDto cancelPayment(Long paymentId) throws PaymentException {
        return null;
    }

    @Override
    public PaymentInitiateResponse retryPayment(Long paymentId) throws PaymentException {
        return null;
    }

//    @Override
//    public RevenueStatisticsResponse getMonthlyRevenue() {
//        return null;
//    }
}
