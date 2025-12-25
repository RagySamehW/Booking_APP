package com.ragy.customerservice.infrastructure.repository;

import com.ragy.customerservice.data.entity.CustomerEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface CustomerRepository extends ReactiveCrudRepository<CustomerEntity, UUID> {
}