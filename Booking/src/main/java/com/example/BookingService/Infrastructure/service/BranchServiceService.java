package com.example.BookingService.Infrastructure.service;

import com.example.BookingService.Data.mapper.ServicesMapper;
import com.example.BookingService.Domain.dto.ServicesResponseDTO;
import com.example.BookingService.Data.repository.BranchServiceRepository;
import com.example.BookingService.Data.repository.ServicesRepository; // Needed for joins
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BranchServiceService {

    private final BranchServiceRepository branchServiceRepository;
    private final ServicesRepository servicesRepository;
    private final ServicesMapper serviceMapper;

    public BranchServiceService(
            BranchServiceRepository branchServiceRepository,
            ServicesRepository servicesRepository,
            ServicesMapper serviceMapper) {
        this.branchServiceRepository = branchServiceRepository;
        this.servicesRepository = servicesRepository;
        this.serviceMapper = serviceMapper;
    }

    public Mono<Integer> getMaxCapacity(Long branchId, Long serviceId) {
        // Delegate directly to the repository query
        return branchServiceRepository.findCapacityByBranchAndService(branchId, serviceId)
                .switchIfEmpty(Mono.error(() -> new RuntimeException(
                        String.format("Capacity rule not found for Branch ID %d and Service ID %d.", branchId, serviceId)
                )));
    }

    public Flux<ServicesResponseDTO> getServicesByBranch(Long branchId) {

        // 1. Get the list of service IDs linked to the branch
        Flux<Long> serviceIdsFlux = branchServiceRepository.findServiceIdsByBranchId(branchId);

        // 2. Look up the full Service Entity for each ID
        return serviceIdsFlux
                .flatMap(servicesRepository::findById) // Uses method reference for lookup
                // 3. Map the found Entities to the Response DTOs
                .map(serviceMapper::toDto)
                .switchIfEmpty(Flux.empty());
    }
}