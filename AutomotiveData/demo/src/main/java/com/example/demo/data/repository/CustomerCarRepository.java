package com.example.demo.data.repository;

import com.example.demo.domain.model.CustomerCar;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerCarRepository extends R2dbcRepository<CustomerCar, Long> {
    // You can add custom query methods here if needed
    // Example:
    // Flux<CustomerCar> findByAutomotiveId(Long automotiveId);
    // Mono<CustomerCar> findByVin(String vin);

}

