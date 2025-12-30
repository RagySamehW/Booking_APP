package com.example.demo.data.repository;

import com.example.demo.data.entity.BranchEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface BranchRepository
        extends ReactiveCrudRepository<BranchEntity, Long> {

    Flux<BranchEntity> findByAutomotiveId(Long automotive_id);
}
