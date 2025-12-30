package com.example.demo.infrastructure.controller;

import com.example.demo.domain.model.CustomerCar;
import com.example.demo.data.service.CustomerCarService;  // ← Import the INTERFACE!
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/customer-cars")
@RequiredArgsConstructor
public class CustomerCarController {

    // This should be the INTERFACE, not CustomerCarServiceImpl!
    private final CustomerCarService CustomerCarServiceImplement;

    @GetMapping
    public Flux<CustomerCar> getAllCustomerCars() {
        return CustomerCarServiceImplement.getAllCustomerCars();
    }

    @GetMapping("/{id}")
    public Mono<CustomerCar> getCustomerCarById(@PathVariable Long id) {
        return CustomerCarServiceImplement.getCustomerCarById(id);
    }

    @PostMapping
    public Mono<CustomerCar> createCustomerCar(@RequestBody CustomerCar customerCar) {
        return CustomerCarServiceImplement.createCustomerCar(customerCar);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteCustomerCar(@PathVariable Long id) {
        return CustomerCarServiceImplement.deleteCustomerCar(id);
    }

    @GetMapping("/vin/{vin}")
    public Mono<CustomerCar> findCustomerCarByVin(@PathVariable String vin) {
        return CustomerCarServiceImplement.findCustomerCarByVin(vin);
    }

    @GetMapping("/count")
    public Mono<Long> countCustomerCars() {
        return CustomerCarServiceImplement.countAllCustomerCars();
    }
}

