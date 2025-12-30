package com.example.demo.data.service;

import com.example.demo.domain.model.Automotive;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AutomotiveService {
    Flux<Automotive> getAllAutomotives();
    Mono<Automotive> getAutomotiveById(Long id);
    Mono<Automotive> createAutomotive(Automotive automotive);
    Mono<Void> deleteAutomotive(Long id);
    Mono<Long> countAllAutomotives();
}