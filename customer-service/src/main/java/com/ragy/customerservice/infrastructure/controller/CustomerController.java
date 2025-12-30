package com.ragy.customerservice.infrastructure.controller;

import com.ragy.customerservice.data.dto.request.CustomerRequest;
import com.ragy.customerservice.data.dto.response.CustomerResponse;
import com.ragy.customerservice.data.service.CustomerServiceInterface;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerServiceInterface service;

    @PostMapping
    public Mono<ResponseEntity<CustomerResponse>> createCustomer(@Valid @RequestBody CustomerRequest request) {
        return service.createCustomer(request)
                .map(r -> ResponseEntity.created(URI.create("/api/customers/" + r.getId())).body(r));
    }

    @GetMapping("/{id}")
    public Mono<CustomerResponse> getCustomer(@PathVariable UUID id) {
        return service.getCustomer(id);
    }
    @GetMapping("/phone/{phone}")
    public Mono<ResponseEntity<CustomerResponse>> getCustomerByPhone(@PathVariable String phone) {
        return service.getCustomerByPhone(phone)
                .map(ResponseEntity::ok)                    // 200 if found
                .defaultIfEmpty(ResponseEntity.notFound().build());  // 404 if empty
    }

    @GetMapping
    public Flux<CustomerResponse> getAllCustomers() {
        return service.getAllCustomers();
    }

    @PutMapping("/{id}")
    public Mono<CustomerResponse> updateCustomer(@PathVariable UUID id, @Valid @RequestBody CustomerRequest request) {
        return service.updateCustomer(id, request);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteCustomer(@PathVariable UUID id) {
        return service.deleteCustomer(id);
    }
}