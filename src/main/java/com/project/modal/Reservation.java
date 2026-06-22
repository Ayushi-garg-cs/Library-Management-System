package com.project.modal;

import com.project.domain.ReservationStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * User who made the reservation
     */
    @NotNull(message = "User is mandatory for reservation")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Book being reserved
     */
    @NotNull(message = "Book is mandatory for reservation")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    /**
     * Current status of the reservation
     */
    @NotNull(message = "Reservation status is mandatory")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ReservationStatus status = ReservationStatus.PENDING;

    /**
     * Date and time when reservation was created
     */
    @NotNull
    @Column(name = "reserved_at", nullable = false)
    private LocalDateTime reservedAt;

    /**
     * Date and time when book became available for pickup
     */
    @Column(name = "available_at")
    private LocalDateTime availableAt;

    /**
     * Date and time until which the book will be held (after becoming available)
     * Usually 48-72 hours after notification
     */
    @Column(name = "available_until")
    private LocalDateTime availableUntil;

    /**
     * Date and time when reservation was fulfilled (book checked out)
     */
    @Column(name = "fulfilled_at")
    private LocalDateTime fulfilledAt;

    /**
     * Date and time when reservation was cancelled or expired
     */
    @Column(name = "cancelled_at")
    private LocalDateTime cancelledAt;

    /**
     * Position in the reservation queue for this book
     */
    @Column(name = "queue_position")
    private Integer queuePosition;

    /**
     * Whether notification email has been sent when book became available
     */
    @Column(name = "notification_sent", nullable = false)
    private Boolean notificationSent = false;

    /**
     * Additional notes or reason for cancellation
     */
    @Column(columnDefinition = "TEXT")
    private String notes;

    /**
     * Record creation timestamp
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Record last update timestamp
     */
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Check if reservation is active (pending or available)
     */
    public boolean isActive() {
        return status == ReservationStatus.PENDING || status == ReservationStatus.AVAILABLE;
    }

    /**
     * Check if reservation can be cancelled
     */
    public boolean canBeCancelled() {
        return status == ReservationStatus.PENDING || status == ReservationStatus.AVAILABLE;
    }

    /**
     * Check if reservation has expired
     */
    public boolean hasExpired() {
        return status == ReservationStatus.AVAILABLE
                && availableUntil != null
                && LocalDateTime.now().isAfter(availableUntil);
    }
}
