package com.ragy.customerservice.data.dto.response;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class CustomerResponse {

    private UUID id;
    private String customerNumber;
    private String name;
    private String phone;
    private String email;
    private Instant createdAt;
    private Instant updatedAt;
}
