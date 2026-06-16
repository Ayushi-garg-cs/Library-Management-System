package com.project.repository;

import com.project.modal.SubscriptionPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionPlanRepository extends JpaRepository<SubscriptionPlan,Long> {
    boolean existsByCode(String planCode);
}
