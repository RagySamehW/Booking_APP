package com.example.demo.data.service;

import com.example.demo.domain.model.CustomerCar;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

// This is the INTERFACE (PROMISE)
public interface CustomerCarService {
    // PROMISE 1: "I can get all customer cars"
    Flux<CustomerCar> getAllCustomerCars();

    // PROMISE 2: "I can get one customer car by ID"
    Mono<CustomerCar> getCustomerCarById(Long id);

    // PROMISE 3: "I can create a new customer car (with validation!)"
    Mono<CustomerCar> createCustomerCar(CustomerCar customerCar);

    // PROMISE 4: "I can delete a customer car"
    Mono<Void> deleteCustomerCar(Long id);

    // PROMISE 5: "I can find a customer car by VIN"
    Mono<CustomerCar> findCustomerCarByVin(String vin);

    // PROMISE 6: "I can count all customer cars"
    Mono<Long> countAllCustomerCars();
}

