package com.example.demo.data.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("customer_cars")
public class CustomerCarEntity {

    @Id
    private Long id;

    @Column("vin")
    private String vin;

    @Column("automotive_id")
    private Long automotiveId;
}