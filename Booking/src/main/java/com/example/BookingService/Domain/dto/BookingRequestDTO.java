package com.example.BookingService.Domain.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class BookingRequestDTO {
    private Long car_id;
    private Long service_id;
    private Long branch_id;
    private LocalDate booking_date;
    private String comments;
}