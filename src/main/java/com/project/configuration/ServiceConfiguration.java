package com.project.configuration;

import com.project.service.ReservationService;
import com.project.service.impl.BookLoanServiceImpl;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {
    private final BookLoanServiceImpl bookLoanService;
    private final ReservationService reservationService;

    public ServiceConfiguration(BookLoanServiceImpl bookLoanService,
                                ReservationService reservationService) {
        this.bookLoanService = bookLoanService;
        this.reservationService = reservationService;
    }

    @PostConstruct
    public void init() {
        // Set ReservationService in BookLoanService after construction
        bookLoanService.setReservationService(reservationService);
    }
}
