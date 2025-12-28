package com.example.BookingService.Data.repository;

import com.example.BookingService.Data.entity.ServicesEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ServicesRepository extends ReactiveCrudRepository <ServicesEntity, Long> {
}