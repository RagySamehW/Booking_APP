package com.example.demo.data.repository;

import com.example.demo.domain.model.Automotive;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutomotiveRepository extends R2dbcRepository<Automotive, Long> {
    // You can add custom query methods here if needed
    // Example:
    // Mono<Automotive> findByName(String name);
}

