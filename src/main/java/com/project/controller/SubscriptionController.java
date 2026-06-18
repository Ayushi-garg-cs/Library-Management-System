package com.project.controller;


import com.project.exception.SubscriptionException;
import com.project.exception.UserException;
import com.project.modal.Subscription;
import com.project.payload.dto.SubscriptionDto;
import com.project.payload.response.ApiResponse;
import com.project.service.SubscriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribe(@Valid @RequestBody SubscriptionDto dto) throws UserException, Exception {
        SubscriptionDto subscriptionDto=subscriptionService.subscribe(dto);
        return ResponseEntity.ok().body(subscriptionDto);
    }

    @GetMapping("/admin")
    public ResponseEntity<?> getAllSubscriptions(){
        int page=0;
        int size=10;
        Pageable pageable=PageRequest.of(page,size);
        List<SubscriptionDto> dtoList=subscriptionService.getAllActiveSubscriptions(pageable);
        return ResponseEntity.ok().body(dtoList);
    }

    @GetMapping("/admin/deactivate-expired")
    public ResponseEntity<?> deactivateExpiredSubscriptions(){
        int page=0;
        int size=10;
        Pageable pageable=PageRequest.of(page,size);
        subscriptionService.deactivateExpiredSubscriptions();
        ApiResponse res=new ApiResponse("Task Done!",true);
        return ResponseEntity.ok().body(res);
    }

    @GetMapping("/user/active")
    public ResponseEntity<?> getUsersActiveSubscriptions(@RequestParam(required = false) Long userId) throws SubscriptionException, UserException {
        SubscriptionDto dto=subscriptionService.getUsersActiveSubscription(userId);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping("/activate")
    public ResponseEntity<?> activateSubscription(@RequestParam Long subscriptionId,@RequestParam Long paymentId) throws UserException, SubscriptionException {
        SubscriptionDto dto=subscriptionService.activateSubscription(subscriptionId,paymentId);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping("/cancel/{subscriptionId}")
    public  ResponseEntity<?> cancelSubscription(@PathVariable Long subscriptionId
                        ,@RequestParam(required = false) String reason) throws UserException, SubscriptionException {
        SubscriptionDto dto=subscriptionService.cancelSubscription(subscriptionId,reason);
        return ResponseEntity.ok().body(dto);

    }
}
