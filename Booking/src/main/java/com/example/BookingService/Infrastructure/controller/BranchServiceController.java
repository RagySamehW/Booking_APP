package com.example.BookingService.Infrastructure.controller;

import com.example.BookingService.Infrastructure.service.BranchServiceService;
import com.example.BookingService.Domain.dto.ServicesResponseDTO; // Assuming the DTO is named ServicesResponseDTO
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/branches")
public class BranchServiceController {

    private final BranchServiceService branchServiceService;

    public BranchServiceController(BranchServiceService branchServiceService) {
        this.branchServiceService = branchServiceService;
    }

    @GetMapping("/{branchId}/services")
    public Flux<ServicesResponseDTO> getServicesByBranch(@PathVariable Long branchId) {
        return branchServiceService.getServicesByBranch(branchId);
    }
}