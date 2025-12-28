package com.example.BookingService.Infrastructure.controller;

// package com.example.BookingService.Infrastructure.controller;

import com.example.BookingService.Domain.dto.BookingRequestDTO;
import com.example.BookingService.Domain.dto.BookingResponseDTO;
import com.example.BookingService.Domain.service.BookingServiceInterface;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDate;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingServiceInterface bookingService;


    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BookingResponseDTO> createBooking(@RequestBody BookingRequestDTO request) {
        // The service returns a Mono<BookingDTO>, which Spring WebFlux handles automatically
        return bookingService.createBooking(request);
    }

    @GetMapping("/car/{carId}")
    public Flux<BookingResponseDTO> getBookingsByCarId(@PathVariable Long carId) {
        // The service returns a Flux<BookingDTO>, which Spring WebFlux handles automatically
        return bookingService.getBookingsByCarId(carId);
    }

    @Data
    private static class RescheduleRequest {
        private LocalDate booking_date;
        private String newComments;
    }


    @PostMapping("/reschedule/{oldBookingId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BookingResponseDTO> rescheduleBooking(
            @PathVariable Long oldBookingId,
            @RequestBody RescheduleRequest request
    ) {
        return bookingService.rescheduleBooking(
                oldBookingId,
                request.getBooking_date(),
                request.getNewComments()
        );
    }
    @PatchMapping("/cancel/{bookingId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<BookingResponseDTO> cancelBooking(@PathVariable Long bookingId) {
        return bookingService.cancelBooking(bookingId);
    }
}