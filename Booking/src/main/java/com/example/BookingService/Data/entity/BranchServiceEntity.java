package com.example.BookingService.Data.entity;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

// This entity maps the capacity limit for a specific combination of service and branch.
@Data
@Table("branch_service")
public class BranchServiceEntity {
    private Long branch_id;
    private Long service_id;
    private Integer capacity_per_day;
}