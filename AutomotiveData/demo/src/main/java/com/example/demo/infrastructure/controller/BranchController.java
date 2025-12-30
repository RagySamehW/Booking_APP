package com.example.demo.infrastructure.controller;
import com.example.demo.data.entity.BranchEntity;
import com.example.demo.domain.model.Branch;
import com.example.demo.data.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/branches")
public class BranchController {

    private final BranchService branchService;


    // Create a branch
    @PostMapping
    public Mono<BranchEntity> createBranch(@RequestBody BranchEntity branch) {

        System.out.println("branch.toString()");
        System.out.println(branch.toString());
        return branchService.createBranch(branch);
    }

//    // Get branches for an automotive
    @GetMapping("/automotive/{automotiveId}")
    public Flux<BranchEntity> getBranchesByAutomotive(
            @PathVariable Long automotiveId) {
        return branchService.getBranchesByAutomotiveId(automotiveId);
    }
}
