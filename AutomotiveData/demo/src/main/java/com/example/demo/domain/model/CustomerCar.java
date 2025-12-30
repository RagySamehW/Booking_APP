package com.example.demo.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCar {
    private Long id;
    private String vin;
    private Long automotiveId;
}