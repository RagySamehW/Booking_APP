package com.example.demo.infrastructure.service;

import com.example.demo.domain.model.CustomerCar;
import com.example.demo.data.service.CustomerCarService;
import com.example.demo.data.repository.CustomerCarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CustomerCarServiceImplement implements CustomerCarService {
    // This class now PROMISES to implement ALL methods from CustomerCarService interface

    private final CustomerCarRepository customerCarRepository;

    // 1. Get all customer cars
    @Override
    public Flux<CustomerCar> getAllCustomerCars() {
        return customerCarRepository.findAll();
    }

    // 2. Get customer car by ID
    @Override
    public Mono<CustomerCar> getCustomerCarById(Long id) {
        return customerCarRepository.findById(id);
    }

    // 3. Create a new customer car (with validation!)
    @Override
    public Mono<CustomerCar> createCustomerCar(CustomerCar customerCar) {
        // BUSINESS RULE 1: VIN must be provided
        if (customerCar.getVin() == null || customerCar.getVin().trim().isEmpty()) {
            return Mono.error(new IllegalArgumentException("VIN is required!"));
        }

        // BUSINESS RULE 2: VIN must be alphanumeric
        if (!customerCar.getVin().matches("^[A-Z0-9]+$")) {
            return Mono.error(new IllegalArgumentException("VIN must contain only letters and numbers!"));
        }

        // BUSINESS RULE 3: Check if VIN already exists
        return customerCarRepository.findAll()
                .filter(existingCar -> existingCar.getVin().equalsIgnoreCase(customerCar.getVin()))
                .hasElements()
                .flatMap(vinExists -> {
                    if (vinExists) {
                        return Mono.error(new IllegalArgumentException("Customer car with this VIN already exists!"));
                    } else {
                        return customerCarRepository.save(customerCar);
                    }
                });
    }

    // 4. Delete a customer car
    @Override
    public Mono<Void> deleteCustomerCar(Long id) {
        return customerCarRepository.deleteById(id);
    }

    // 5. Find customer car by VIN
    @Override
    public Mono<CustomerCar> findCustomerCarByVin(String vin) {
        return customerCarRepository.findAll()
                .filter(car -> car.getVin().equalsIgnoreCase(vin))
                .next();  // Get first match or empty
    }

    // 6. Count all customer cars
    @Override
    public Mono<Long> countAllCustomerCars() {
        return customerCarRepository.count();
    }
}

