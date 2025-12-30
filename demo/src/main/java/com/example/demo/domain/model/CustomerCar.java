package com.example.demo.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCar {

    @Id
    private Long id;

    private String vin;
    private Long automotiveId;
}