package com.project.event.publisher;

import com.project.modal.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    //it will publish the event
    public void publishPaymentSuccessEvent(Payment payment){
        applicationEventPublisher.publishEvent(payment);
    }
}
