package com.example.demo.data.service;

import com.example.demo.data.entity.BranchEntity;
import com.example.demo.domain.model.Branch;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BranchService {

    Mono<BranchEntity> createBranch(BranchEntity branch);

    Flux<BranchEntity> getBranchesByAutomotiveId(Long automotiveId);
}
