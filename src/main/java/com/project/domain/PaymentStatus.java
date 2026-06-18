package com.project.domain;

public enum PaymentStatus {

        PENDING,

        SUCCESS,

        /**
         * Payment failed due to insufficient funds, card decline, etc.
         */
        FAILED,

        /**
         * Payment was cancelled by user
         */
        CANCELLED,

        /**
         * Payment was refunded
         */
        REFUNDED,

        /**
         * Payment is being processed by gateway
         */
        PROCESSING
    }

