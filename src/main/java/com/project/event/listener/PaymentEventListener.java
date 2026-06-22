package com.project.event.listener;

import com.project.exception.SubscriptionException;
import com.project.modal.Payment;
import com.project.service.SubscriptionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentEventListener {
    private final SubscriptionService subscriptionService;

    @Async
    @EventListener
    @Transactional
    public void handlePaymentSuccessEvent(Payment payment) throws SubscriptionException {
        switch (payment.getPaymentType()) {
            case FINE:
            case DAMAGED_BOOK_PENALTY:
            case LOST_BOOK_PENALTY:
//                fineService.markFineAsPaid(
//                        event.getFineId(),
//                        event.getAmount(),
//                        event.getTransactionId()
//                );
                break;
            case MEMBERSHIP:
                subscriptionService.activateSubscription(payment.getSubscription().getId());
                break;
        }
    }
}
