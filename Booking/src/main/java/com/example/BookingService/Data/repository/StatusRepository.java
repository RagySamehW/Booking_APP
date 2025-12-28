package com.example.BookingService.Data.repository;

import com.example.BookingService.Data.entity.StatusEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends ReactiveCrudRepository<StatusEntity, Long> {
}