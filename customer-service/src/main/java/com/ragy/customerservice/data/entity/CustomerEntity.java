package com.ragy.customerservice.data.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.time.Instant;
import java.util.UUID;

@Data
@Table("customers")
public class CustomerEntity {

    @Id
    private UUID id;
    private String customerNumber;
    private String name;
    private String phone;
    private String email;
    private Instant createdAt;
    private Instant updatedAt;
}
