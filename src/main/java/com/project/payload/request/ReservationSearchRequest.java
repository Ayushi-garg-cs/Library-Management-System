package com.project.payload.request;

import com.project.domain.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationSearchRequest {
    // User filter
    private Long userId;

    // Book filter
    private Long bookId;

    // Status filter
    private ReservationStatus status;

    // Active only (PENDING or AVAILABLE)
    private Boolean activeOnly;

    // Pagination
    private int page = 0;
    private int size = 20;

    // Sorting
    private String sortBy = "reservedAt"; // reservedAt, availableAt, queuePosition, status
    private String sortDirection = "DESC"; // ASC or DESC
}
