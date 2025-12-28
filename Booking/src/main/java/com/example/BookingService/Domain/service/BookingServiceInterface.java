package com.example.BookingService.Domain.service;

// package com.example.BookingService.Domain.service;

import com.example.BookingService.Domain.dto.BookingRequestDTO;
import com.example.BookingService.Domain.dto.BookingResponseDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDate;


public interface BookingServiceInterface {

    Mono<BookingResponseDTO> createBooking(BookingRequestDTO request);
    Flux<BookingResponseDTO> getBookingsByCarId(Long carId);
    Mono<BookingResponseDTO> rescheduleBooking(Long oldBookingId, LocalDate newDate, String newComments);
    Mono<BookingResponseDTO> cancelBooking(Long bookingId);
}