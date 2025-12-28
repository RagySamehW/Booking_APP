package com.example.BookingService.Domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicesResponseDTO {

    private Long id;
    private String name;
}