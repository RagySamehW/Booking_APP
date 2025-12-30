package com.ragy.customerservice.data.service;

import com.ragy.customerservice.data.dto.request.CustomerRequest;
import com.ragy.customerservice.data.dto.response.CustomerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface CustomerServiceInterface {

    Mono<CustomerResponse> createCustomer(CustomerRequest request);
    Mono<CustomerResponse> getCustomer(UUID id);
    Flux<CustomerResponse> getAllCustomers();
    Mono<CustomerResponse> updateCustomer(UUID id, CustomerRequest request);
    Mono<Void> deleteCustomer(UUID id);
    Mono<CustomerResponse> getCustomerByPhone(String phone);

}
