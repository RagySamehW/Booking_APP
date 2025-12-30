package com.example.demo.infrastructure.service;

import com.example.demo.data.entity.BranchEntity;
import com.example.demo.data.service.BranchService;
import com.example.demo.data.repository.AutomotiveRepository;
import com.example.demo.data.repository.BranchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class BranchServiceImplement implements BranchService {

    private final BranchRepository branchRepository;
    private final AutomotiveRepository automotiveRepository;


    @Override
    public Mono<BranchEntity> createBranch(BranchEntity branch) {

        // 1️⃣ Validate automotive exists
        try{

            System.out.println(branch.toString());
            var x = automotiveRepository.findById(branch.getAutomotiveId())
//                    .switchIfEmpty(
//                            Mono.error(new RuntimeException("Automotive not found"))
//                    )
                    // 2️⃣ Save branch
                    .flatMap(a ->
                            branchRepository.save(branch)

                    );
            System.out.println("failed here");
            System.out.println(x);
            return x;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return Mono.error(new IllegalArgumentException());
        }
    }

    @Override
    public Flux<BranchEntity> getBranchesByAutomotiveId(Long automotiveId) {
        return branchRepository.findByAutomotiveId(automotiveId);
    }

}
