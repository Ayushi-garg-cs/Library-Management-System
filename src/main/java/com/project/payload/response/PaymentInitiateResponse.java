package com.project.payload.response;

import com.project.domain.PaymentGateway;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentInitiateResponse {
    private Long paymentId;

    private PaymentGateway gateway;

    private String transactionId;

    // Razorpay specific fields
    private String razorpayOrderId;


    private Long amount;

    private String currency;

    private String description;

    // Frontend should redirect user to this URL for payment
    private String checkoutUrl;

    private String message;

    private Boolean success;
}
