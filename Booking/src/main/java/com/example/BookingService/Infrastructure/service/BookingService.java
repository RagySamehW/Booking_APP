package com.example.BookingService.Infrastructure.service;

import com.example.BookingService.Data.entity.BookingEntity;
import com.example.BookingService.Data.mapper.BookingMapper;
import com.example.BookingService.Domain.dto.BookingRequestDTO;
import com.example.BookingService.Domain.dto.BookingResponseDTO;
import com.example.BookingService.Domain.model.BookingModel;
import com.example.BookingService.Domain.model.BookingStatus;
import com.example.BookingService.Domain.service.BookingServiceInterface;
import com.example.BookingService.Data.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator; // 🚨 IMPORTANT: Used for transactional control
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@Service
public class BookingService implements BookingServiceInterface {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final BranchServiceService branchServiceService; // 1. INJECT CAPACITY SERVICE
    private final TransactionalOperator transactionalOperator; // 🚨 IMPORTANT: For atomicity

    private int countConsecutiveStatus(
            List<BookingModel> bookings,
            BookingStatus targetStatus
    ) {
        int count = 0;
        for (BookingModel booking : bookings) {
            BookingStatus status = booking.getStatus();
            if (status == BookingStatus.COMPLETED) {
                break; // reset rule
            }
            if (status == targetStatus) {
                count++;
            } else {
                break;
            }
        }
        return count;
    }

    private Mono<List<LocalDate>> findClosestAvailableDates(
            Long branchId,
            Long serviceId,
            int maxCapacity
    ) {
        LocalDate startDate = LocalDate.now().plusDays(1);

        return Flux
                .generate(() -> startDate, (date, sink) -> {
                    sink.next(date);
                    return date.plusDays(1);
                })
                .cast(LocalDate.class)
                .flatMap(date ->
                        bookingRepository
                                .countActiveBookings(branchId, serviceId, date)
                                .filter(count -> count < maxCapacity)
                                .map(count -> date)
                )
                .take(3)
                .collectList();
    }


    @Override
    public Mono<BookingResponseDTO> createBooking(BookingRequestDTO request) {

        Long carId = request.getCar_id();
        Long branchId = request.getBranch_id();
        Long serviceId = request.getService_id();
        LocalDate requestedDate = request.getBooking_date();

        return bookingRepository.existsPendingBookingByCarId(carId)
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new IllegalStateException(
                                String.format("Car ID %d already has a pending booking and cannot book again.", carId)
                        ));
                    }

                    return branchServiceService.getMaxCapacity(branchId, serviceId)
                            .flatMap(maxCapacity -> findClosestAvailableDates(branchId, serviceId, maxCapacity))
                            .flatMap(availableDates -> {

                                if (availableDates.isEmpty()) {
                                    availableDates.add(LocalDate.now().plusDays(1));
                                }

                                LocalDate chosenDate;
                                if (requestedDate != null && availableDates.contains(requestedDate)) {
                                    chosenDate = requestedDate;
                                } else if (requestedDate != null) {
                                    return Mono.error(new IllegalStateException(
                                            String.format("Requested date %s is not available. Please choose one of the nearest available dates: %s",
                                                    requestedDate, availableDates)
                                    ));
                                } else {
                                    chosenDate = availableDates.get(0);
                                }

                                BookingEntity entity = bookingMapper.toEntity(request);
                                entity.setBooking_date(chosenDate);
                                entity.setStatus_id((long) BookingStatus.PENDING.getCode());
                                entity.setCreatedAt(LocalDateTime.now());
                                entity.setUpdatedAt(LocalDateTime.now());

                                return bookingRepository.save(entity);
                            });
                })
                .as(transactionalOperator::transactional)
                .map(bookingMapper::toDomain)
                .map(bookingMapper::toDto);
    }

    @Override
    public Mono<BookingResponseDTO> rescheduleBooking(Long oldBookingId, LocalDate requestedDate, String newComments) {

        return bookingRepository.findById(oldBookingId)
                .flatMap(oldBookingEntity -> {

                    if (oldBookingEntity.getStatus_id() != (long) BookingStatus.PENDING.getCode()) {
                        return Mono.error(() -> new IllegalStateException("Only PENDING bookings can be rescheduled."));
                    }

                    return bookingRepository.findLastBookings(oldBookingEntity.getCar_id(), 3)
                            .map(bookingMapper::toDomain)
                            .collectList()
                            .flatMap(bookings -> {
                                int consecutiveReschedules = countConsecutiveStatus(bookings, BookingStatus.RESCHEDULED);
                                if (consecutiveReschedules >= 3) {
                                    return Mono.error(new IllegalStateException(
                                            "You have reached the maximum of 3 consecutive reschedules. " +
                                                    "Please visit the Automotive Group."
                                    ));
                                }

                                Long branchId = oldBookingEntity.getBranch_id();
                                Long serviceId = oldBookingEntity.getService_id();

                                return branchServiceService.getMaxCapacity(branchId, serviceId)
                                        .flatMap(maxCapacity -> findClosestAvailableDates(branchId, serviceId, maxCapacity))
                                        .flatMap(availableDates -> {
                                            if (availableDates.isEmpty()) {
                                                // fallback
                                                availableDates.add(LocalDate.now().plusDays(1));
                                            }

                                            // Step 1: Check if requested date is one of the available ones
                                            LocalDate chosenDate;
                                            if (requestedDate != null && availableDates.contains(requestedDate)) {
                                                chosenDate = requestedDate;
                                            } else {
                                                // Throw exception if requested date is invalid
                                                return Mono.error(new IllegalStateException(
                                                        String.format("Requested date %s is not available. Please choose one of the nearest available dates: %s",
                                                                requestedDate, availableDates)
                                                ));
                                            }


                                            // Step 2: Update old booking
                                            oldBookingEntity.setStatus_id((long) BookingStatus.RESCHEDULED.getCode());
                                            oldBookingEntity.setUpdatedAt(LocalDateTime.now());

                                            // Step 3: Create new booking
                                            BookingEntity newBookingEntity = new BookingEntity(
                                                    oldBookingEntity.getService_id(),
                                                    oldBookingEntity.getCar_id(),
                                                    oldBookingEntity.getBranch_id(),
                                                    chosenDate,
                                                    newComments
                                            );
                                            newBookingEntity.setStatus_id((long) BookingStatus.PENDING.getCode());
                                            newBookingEntity.setBooking_id(null);
                                            newBookingEntity.setCreatedAt(LocalDateTime.now());
                                            newBookingEntity.setUpdatedAt(LocalDateTime.now());

                                            // Step 4: Save both
                                            return bookingRepository.save(oldBookingEntity)
                                                    .then(bookingRepository.save(newBookingEntity));
                                        });
                            });
                })
                .as(transactionalOperator::transactional)
                .map(bookingMapper::toDomain)
                .map(bookingMapper::toDto);
    }


    @Override
    public Flux<BookingResponseDTO> getBookingsByCarId(Long carId) {
        return bookingRepository.findByCarId(carId)
                .map(bookingMapper::toDomain)
                .map(bookingMapper::toDto);
    }

    @Override
    public Mono<BookingResponseDTO> cancelBooking(Long bookingId) {

        return bookingRepository.findById(bookingId)
                .switchIfEmpty(Mono.error(
                        new ResponseStatusException(
                                NOT_FOUND,
                                "Booking not found with ID: " + bookingId
                        )
                ))
                .flatMap(bookingEntity -> {

                    Long currentStatusId = bookingEntity.getStatus_id();

                    if (!currentStatusId.equals(
                            (long) BookingStatus.PENDING.getCode()
                    )) {

                        if (currentStatusId.equals(
                                (long) BookingStatus.CANCELLED.getCode()
                        )) {
                            return Mono.error(new ResponseStatusException(
                                    CONFLICT,
                                    String.format(
                                            "Booking ID %d is already cancelled.",
                                            bookingId
                                    )
                            ));
                        }

                        return Mono.error(new ResponseStatusException(
                                BAD_REQUEST,
                                String.format(
                                        "Booking ID %d cannot be cancelled as its status is not PENDING.",
                                        bookingId
                                )
                        ));
                    }

                    bookingEntity.setStatus_id(
                            (long) BookingStatus.CANCELLED.getCode()
                    );
                    bookingEntity.setUpdatedAt(LocalDateTime.now());

                    return bookingRepository.save(bookingEntity);
                })
                .as(transactionalOperator::transactional)
                .map(bookingMapper::toDomain)
                .map(bookingMapper::toDto);
    }
}