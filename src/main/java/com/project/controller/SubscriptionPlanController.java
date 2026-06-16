package com.project.controller;

import com.project.exception.UserException;
import com.project.payload.dto.SubscriptionPlanDto;
import com.project.payload.response.ApiResponse;
import com.project.repository.SubscriptionPlanRepository;
import com.project.service.SubscriptionPlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscription-plans")
@RequiredArgsConstructor
public class SubscriptionPlanController {

    private final SubscriptionPlanService planService;


    @GetMapping
    public ResponseEntity<List<SubscriptionPlanDto>> getAllSubscriptionPlans() {
        return ResponseEntity.ok(planService.getAllSubscriptionPlan());
    }

    @PostMapping("/admin/create")
    public ResponseEntity<SubscriptionPlanDto> addSubscriptionPlan(@Valid @RequestBody SubscriptionPlanDto subscriptionPlanDto) throws Exception, UserException {
        return ResponseEntity.ok(planService.createSubscriptionPlan(subscriptionPlanDto));
    }

    @PostMapping("/admin/{id}")
    public ResponseEntity<?>  updateSubscriptionPlan(@PathVariable Long id,@Valid @RequestBody SubscriptionPlanDto subscriptionPlanDto) throws UserException, Exception {
        return ResponseEntity.ok(planService.updateSubscriptionPlan(id,subscriptionPlanDto));
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<?> deleteSubscriptionPlan(@PathVariable Long id) throws UserException, Exception {
        planService.deleteSubscriptionPlan(id);
        ApiResponse res=new ApiResponse("Plan deleted successfully",true);
        return ResponseEntity.ok(res);
    }


}
