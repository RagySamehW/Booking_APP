package com.example.BookingService.Data.repository;

import com.example.BookingService.Data.entity.BookingEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Repository
public interface BookingRepository
        extends ReactiveCrudRepository<BookingEntity, Long> {

    @Query("SELECT * FROM booking WHERE car_id = :carId")
    Flux<BookingEntity> findByCarId(Long carId);

    @Query("SELECT COUNT(*) FROM booking " +
            "WHERE branch_id = :branchId AND service_id = :serviceId AND booking_date = :date AND status_id = 1")
    Mono<Long> countActiveBookings(Long branchId, Long serviceId, LocalDate date);

    @Query("SELECT EXISTS(SELECT 1 FROM booking WHERE car_id = $1 AND status_id = 1)")
    Mono<Boolean> existsPendingBookingByCarId(Long carId);

    @Query("SELECT * FROM booking WHERE car_id = :carId ORDER BY booking_date DESC LIMIT :limit")
    Flux<BookingEntity> findLastBookings(Long carId, int limit);

    Mono<BookingEntity> findTopByCarIdOrderByCreatedAtDesc(Long carId);

}