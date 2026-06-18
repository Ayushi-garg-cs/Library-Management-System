package com.project.domain;

public enum PaymentGateway {
    /**
     * Razorpay payment gateway (India-focused)
     */
    RAZORPAY,

    /**
     * Stripe payment gateway (Global)
     */
    STRIPE,

    /**
     * Cash payment at library counter
     */
    CASH,

    /**
     * Admin manual adjustment
     */
    MANUAL
}
