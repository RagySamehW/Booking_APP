package com.example.BookingService.Data.repository;

import com.example.BookingService.Data.entity.BranchServiceEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BranchServiceRepository extends ReactiveCrudRepository<BranchServiceEntity, Long> {
    @Query("SELECT capacity_per_day FROM branch_service WHERE branch_id = :branchId AND service_id = :serviceId")
    Mono<Integer> findCapacityByBranchAndService(Long branchId, Long serviceId);
    @Query("SELECT service_id FROM branch_service WHERE branch_id = :branchId")
    Flux<Long> findServiceIdsByBranchId(Long branchId);
}