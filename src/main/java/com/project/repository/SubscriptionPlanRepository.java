package com.project.repository;

import com.project.modal.SubscriptionPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionPlanRepository extends JpaRepository<SubscriptionPlan,Long> {
    boolean existsByPlanCode(String planCode);
    SubscriptionPlan findByPlanCode(String planCode) throws Exception;
}
