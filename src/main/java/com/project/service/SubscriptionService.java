package com.project.service;

import com.project.exception.SubscriptionException;
import com.project.exception.UserException;
import com.project.payload.dto.SubscriptionDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SubscriptionService {
    /**
     * Create new subscription with payment
     */
    SubscriptionDto subscribe(SubscriptionDto subscriptionDto) throws Exception, UserException;

    /**
     * Get active subscription for user
     */
    SubscriptionDto getUsersActiveSubscription(Long userId) throws SubscriptionException, UserException;

    /**
     * Get all subscriptions for user
     */
    List<SubscriptionDto> getUserSubscriptions(Long userId) throws SubscriptionException, UserException;

    /**
     * Renew subscription
     */
//    PaymentInitiateResponse renewSubscription(Long subscriptionId, SubscribeRequest request) throws SubscriptionException, UserException, PaymentException;

    /**
     * Cancel subscription
     */
    SubscriptionDto cancelSubscription(Long subscriptionId, String reason) throws SubscriptionException;

    /**
     * Get subscription by ID
     */
    SubscriptionDto getSubscriptionById(Long id) throws SubscriptionException;

    /**
     * Verify and activate subscription after successful payment
     */
    SubscriptionDto activateSubscription(Long subscriptionId, Long paymentId) throws SubscriptionException;

    /**
     * Get all active subscriptions (Admin)
     */
    List<SubscriptionDto> getAllActiveSubscriptions(Pageable pageable);

    /**
     * Deactivate expired subscriptions (Scheduler)
     */
    void deactivateExpiredSubscriptions();

    /**
     * Check if user has valid subscription
     */
    boolean hasValidSubscription(Long userId);
}
