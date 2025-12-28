package com.example.BookingService.Domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BranchServiceModel {
    private Long branch_id;
    private Long service_id;
    private Integer capacity_per_day;
}
