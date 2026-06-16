package com.project.service.impl;

import com.project.exception.UserException;
import com.project.mapper.SubscriptionPlanMapper;
import com.project.modal.SubscriptionPlan;
import com.project.modal.User;
import com.project.payload.dto.SubscriptionPlanDto;
import com.project.payload.dto.UserDto;
import com.project.repository.SubscriptionPlanRepository;
import com.project.service.SubscriptionPlanService;
import com.project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubscriptionPlanServiceImpl implements SubscriptionPlanService {

    private final SubscriptionPlanRepository planRepository;
    private final SubscriptionPlanMapper planMapper;
    private final UserService userService;


    @Override
    public SubscriptionPlanDto createSubscriptionPlan(SubscriptionPlanDto planDto) throws Exception, UserException {
        if(planRepository.existsByCode(planDto.getPlanCode())){
            throw new Exception("Plan code already exists");
        }
        SubscriptionPlan subscriptionPlan = planMapper.toEntity(planDto);
        UserDto currentUser=userService.getcurrentUser();
        subscriptionPlan.setCreatedBy(String.valueOf(currentUser));
        subscriptionPlan.setUpdatedBy(String.valueOf(currentUser));
        planRepository.save(subscriptionPlan);
        return planMapper.toDto(subscriptionPlan);
    }

    @Override
    public SubscriptionPlanDto updateSubscriptionPlan(Long planId, SubscriptionPlanDto planDto) throws Exception, UserException {
        SubscriptionPlan existingPlan= planRepository.findById(planId).orElseThrow(() -> new Exception("Plan not found!"));
        planMapper.updateEntity(existingPlan, planDto);
        UserDto currentUser=userService.getcurrentUser();
        existingPlan.setUpdatedBy(String.valueOf(currentUser));
        SubscriptionPlan updatedPlan=planRepository.save(existingPlan);
        return planMapper.toDto(updatedPlan);
    }

    @Override
    public void deleteSubscriptionPlan(Long planId) throws Exception {
        SubscriptionPlan existingPlan= planRepository.findById(planId).orElseThrow(() -> new Exception("Plan not found!"));
        planRepository.delete(existingPlan);
    }

    @Override
    public List<SubscriptionPlanDto> getAllSubscriptionPlan() {
        List<SubscriptionPlan> subscriptionPlans= planRepository.findAll();
        return subscriptionPlans.stream().map(planMapper::toDto).collect(Collectors.toList());
    }
}
