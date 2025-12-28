package com.example.BookingService.Domain.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingModel {

    private Long id;
    private Long service_id;
    private Long car_id;
    private Long branch_id;
    private LocalDate booking_date;
    private BookingStatus status;
    private String comments;
}

