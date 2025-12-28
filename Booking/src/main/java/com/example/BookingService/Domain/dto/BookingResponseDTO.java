package com.example.BookingService.Domain.dto;

// package com.example.BookingService.Domain.dto;
import com.example.BookingService.Domain.model.BookingStatus;
import java.time.LocalDate;
import lombok.Data;

@Data
public class BookingResponseDTO {
    private Long id;
    private Long service_id;
    private Long car_id;
    private Long branch_id;
    private LocalDate booking_date;
    private BookingStatus status; // Use the Domain Enum here
    private String comments;
}
