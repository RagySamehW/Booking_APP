package com.ragy.customerservice.infrastructure.service;

import com.ragy.customerservice.data.dto.request.CustomerRequest;
import com.ragy.customerservice.data.dto.response.CustomerResponse;
import com.ragy.customerservice.data.entity.CustomerEntity;
import com.ragy.customerservice.domain.model.Customer;
import com.ragy.customerservice.data.mapper.CustomerMapper;
import com.ragy.customerservice.data.service.CustomerServiceInterface;
import com.ragy.customerservice.infrastructure.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

@Service
public class CustomerService implements CustomerServiceInterface {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<CustomerResponse> getCustomerByPhone(String phone) {
        return repository.findByPhone(phone)
                .map(CustomerMapper::toDomain)
                .map(CustomerMapper::toResponse);
    }

    @Override
    public Mono<CustomerResponse> createCustomer(CustomerRequest request) {
        CustomerEntity entity = new CustomerEntity();
        entity.setName(request.getName());
        entity.setPhone(request.getPhone());
        entity.setEmail(request.getEmail());

        return repository.save(entity)
                .flatMap(saved -> repository.findById(saved.getId()))
                .map(CustomerMapper::toDomain)
                .map(CustomerMapper::toResponse);
    }

    @Override
    public Mono<CustomerResponse> getCustomer(UUID id) {
        return repository.findById(id)
                .map(CustomerMapper::toDomain)
                .map(CustomerMapper::toResponse);
    }

    @Override
    public Flux<CustomerResponse> getAllCustomers() {
        return repository.findAll()
                .map(CustomerMapper::toDomain)
                .map(CustomerMapper::toResponse);
    }

    @Override
    public Mono<CustomerResponse> updateCustomer(UUID id, CustomerRequest request) {
        return repository.findById(id)
                .flatMap(entity -> {
                    if (request.getName() != null) entity.setName(request.getName());
                    if (request.getPhone() != null) entity.setPhone(request.getPhone());
                    if (request.getEmail() != null) entity.setEmail(request.getEmail());
                    entity.setUpdatedAt(Instant.now());
                    return repository.save(entity);
                })
                .map(CustomerMapper::toDomain)
                .map(CustomerMapper::toResponse);
    }

    @Override
    public Mono<Void> deleteCustomer(UUID id) {
        return repository.deleteById(id);
    }
}