package com.project.service;

import com.project.exception.UserException;
import com.project.modal.SubscriptionPlan;
import com.project.payload.dto.SubscriptionPlanDto;

import java.util.List;

public interface SubscriptionPlanService {
    SubscriptionPlanDto createSubscriptionPlan(SubscriptionPlanDto planDto) throws Exception, UserException;
    SubscriptionPlanDto updateSubscriptionPlan(Long planId, SubscriptionPlanDto planDto) throws Exception, UserException;
    void deleteSubscriptionPlan(Long planId) throws Exception;
    List<SubscriptionPlanDto> getAllSubscriptionPlan();
}
