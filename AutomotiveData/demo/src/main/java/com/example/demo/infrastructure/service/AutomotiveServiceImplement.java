package com.example.demo.infrastructure.service;

import com.example.demo.domain.model.Automotive;
import com.example.demo.data.service.AutomotiveService;
import com.example.demo.data.repository.AutomotiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AutomotiveServiceImplement implements AutomotiveService {

    private final AutomotiveRepository automotiveRepository;

    @Override
    public Flux<Automotive> getAllAutomotives() {
        return automotiveRepository.findAll();
    }

    @Override
    public Mono<Automotive> getAutomotiveById(Long id) {
        return automotiveRepository.findById(id);
    }

    @Override
    public Mono<Automotive> createAutomotive(Automotive automotive) {
        // Add validation if needed (e.g., name must not be empty)
        if (automotive.getName() == null || automotive.getName().trim().isEmpty()) {
            return Mono.error(new IllegalArgumentException("Name is required!"));
        }
        return automotiveRepository.save(automotive);
    }

    @Override
    public Mono<Void> deleteAutomotive(Long id) {
        return automotiveRepository.deleteById(id);
    }

    @Override
    public Mono<Long> countAllAutomotives() {
        return automotiveRepository.count();
    }
}