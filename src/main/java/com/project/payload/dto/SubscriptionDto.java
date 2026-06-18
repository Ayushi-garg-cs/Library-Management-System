package com.project.payload.dto;

import com.project.modal.SubscriptionPlan;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscriptionDto {
    private Long id;

    @NotNull(message = "User ID is mandatory")
    private Long userId;

    private String userName;
    private String userEmail;

    @NotNull(message = "Subscription plan ID is mandatory")
    private Long planId;

    private String planName;
    private String planCode;
    private Long price;
    private String currency;
    private Double priceInMajorUnits;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isActive;
    private Integer maxBooksAllowed;
    private Integer maxDaysPerBook;
    private Boolean autoRenew;
    private LocalDateTime cancelledAt;
    private String cancellationReason;
    private String notes;
    private Long daysRemaining;
    private Boolean isValid;
    private Boolean isExpired;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
