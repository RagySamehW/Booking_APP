package com.ragy.customerservice.data.mapper;

import com.ragy.customerservice.data.dto.response.CustomerResponse;
import com.ragy.customerservice.domain.model.Customer;
import com.ragy.customerservice.data.entity.CustomerEntity;

public final class CustomerMapper {

    private CustomerMapper() {}

    public static CustomerResponse toResponse(Customer c) {
        if (c == null) return null;
        CustomerResponse r = new CustomerResponse();
        r.setId(c.getId());
        r.setCustomerNumber(c.getCustomerNumber());
        r.setName(c.getName());
        r.setPhone(c.getPhone());
        r.setEmail(c.getEmail());
        r.setCreatedAt(c.getCreatedAt());
        r.setUpdatedAt(c.getUpdatedAt());
        return r;
    }

    public static CustomerEntity toEntity(Customer domain) {
        if (domain == null) return null;
        CustomerEntity e = new CustomerEntity();
        e.setId(domain.getId());
        e.setCustomerNumber(domain.getCustomerNumber());
        e.setName(domain.getName());
        e.setPhone(domain.getPhone());
        e.setEmail(domain.getEmail());
        e.setCreatedAt(domain.getCreatedAt());
        e.setUpdatedAt(domain.getUpdatedAt());
        return e;
    }

    public static Customer toDomain(CustomerEntity entity) {
        if (entity == null) return null;
        Customer c = new Customer();
        c.setId(entity.getId());
        c.setCustomerNumber(entity.getCustomerNumber());
        c.setName(entity.getName());
        c.setPhone(entity.getPhone());
        c.setEmail(entity.getEmail());
        c.setCreatedAt(entity.getCreatedAt());
        c.setUpdatedAt(entity.getUpdatedAt());
        return c;
    }
}