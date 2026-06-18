package com.project.service.impl;

import com.project.exception.SubscriptionException;
import com.project.exception.UserException;
import com.project.mapper.SubscriptionMapper;
import com.project.modal.Subscription;
import com.project.modal.SubscriptionPlan;
import com.project.modal.User;
import com.project.payload.dto.SubscriptionDto;
import com.project.payload.dto.UserDto;
import com.project.repository.SubscriptionPlanRepository;
import com.project.repository.SubscriptionRepository;
import com.project.service.SubscriptionService;
import com.project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubscriptionImpl implements SubscriptionService {

    private final SubscriptionPlanRepository subscriptionPlanRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionMapper subscriptionMapper;
    private final UserService userService;


    @Override
    public SubscriptionDto subscribe(SubscriptionDto subscriptionDto) throws Exception, UserException {
        UserDto user=userService.getcurrentUser();

        SubscriptionPlan plan= subscriptionPlanRepository
                .findById(subscriptionDto.getPlanId())
                .orElseThrow(()-> new Exception("Plan not found!"));


        //Optional<Sub>
        Subscription subscription = subscriptionMapper.toEntity(subscriptionDto);
        subscription.initializeFromPlan();
        subscription.setIsActive(false);
        Subscription savedSubscription=subscriptionRepository.save(subscription);
        //create payment(todo)

        return subscriptionMapper.toDTO(savedSubscription);
    }

    @Override
    public SubscriptionDto getUsersActiveSubscription(Long userId) throws SubscriptionException, UserException {
        UserDto userdto=userService.getcurrentUser();
        Subscription subscription=subscriptionRepository
                .findActiveSubscriptionByUserId(userdto.getId())
                .orElseThrow(()->new SubscriptionException("No Active subscription found!"));

        return subscriptionMapper.toDTO(subscription);
    }

    @Override
    public List<SubscriptionDto> getUserSubscriptions(Long userId) throws SubscriptionException, UserException {
        return List.of();
    }

    @Override
    public SubscriptionDto cancelSubscription(Long subscriptionId, String reason) throws SubscriptionException {
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new SubscriptionException(
                        "Subscription not found with ID: " + subscriptionId));

        if (!subscription.getIsActive()) {
            throw new SubscriptionException("Subscription is already inactive");
        }

        // Mark as cancelled
        subscription.setIsActive(false);
        subscription.setCancelledAt(LocalDateTime.now());
        subscription.setCancellationReason(reason != null ? reason : "Cancelled by user");

        subscription = subscriptionRepository.save(subscription);
        return subscriptionMapper.toDTO(subscription);
    }

    @Override
    public SubscriptionDto getSubscriptionById(Long id) throws SubscriptionException {
        return null;
    }

    @Override
    public SubscriptionDto activateSubscription(Long subscriptionId, Long paymentId) throws SubscriptionException {
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new SubscriptionException(
                        "Subscription not found with ID: " + subscriptionId));
        //verify payment(todo)

        subscription.setIsActive(true);
        subscription=subscriptionRepository.save(subscription);
        return subscriptionMapper.toDTO(subscription);
    }

    @Override
    public List<SubscriptionDto> getAllActiveSubscriptions(Pageable pageable) {
        List<Subscription> subscriptionsList=subscriptionRepository.findAll();
        return subscriptionMapper.toDTOList(subscriptionsList);
    }

    @Override
    public void deactivateExpiredSubscriptions() {
        List<Subscription> expiredSubscription=subscriptionRepository
                .findExpiredActiveSubscriptions(LocalDate.now());
        for (Subscription subscription:expiredSubscription) {
            subscription.setIsActive(false);
            subscriptionRepository.save(subscription);
        }
    }

    @Override
    public boolean hasValidSubscription(Long userId) {
        return false;
    }
}
