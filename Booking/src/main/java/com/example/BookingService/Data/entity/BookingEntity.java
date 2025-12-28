package com.example.BookingService.Data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("booking")
public class BookingEntity {
    @Id
    private Long booking_id;
    private Long service_id;
    private Long car_id;
    private Long branch_id;
    private LocalDate booking_date;
    private Long status_id;
    private String comments;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public BookingEntity(Long service_id, Long car_id, Long branch_id, LocalDate booking_date, String comments) {
        this.service_id = service_id;
        this.car_id = car_id;
        this.branch_id = branch_id;
        this.booking_date = booking_date;
        this.status_id = 1L;
        this.comments = comments;
    }
}