package com.example.demo.infrastructure.controller;

import com.example.demo.domain.model.Automotive;
import com.example.demo.data.service.AutomotiveService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/automotives")
@RequiredArgsConstructor
public class AutomotiveController {

    private final AutomotiveService automotiveService;

    @GetMapping
    public Flux<Automotive> getAllAutomotives() {
        return automotiveService.getAllAutomotives();
    }

    @GetMapping("/{id}")
    public Mono<Automotive> getAutomotiveById(@PathVariable Long id) {
        return automotiveService.getAutomotiveById(id);
    }

    @PostMapping
    public Mono<Automotive> createAutomotive(@RequestBody Automotive automotive) {
        return automotiveService.createAutomotive(automotive);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteAutomotive(@PathVariable Long id) {
        return automotiveService.deleteAutomotive(id);
    }

    @GetMapping("/count")
    public Mono<Long> countAutomotives() {
        return automotiveService.countAllAutomotives();
    }
}