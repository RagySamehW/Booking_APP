package com.ragy.customerservice.infrastructure.controller;

import io.r2dbc.spi.Connection;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class TestController {

    private final ConnectionFactory connectionFactory;

    public TestController(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @GetMapping("/test-db")
    public Mono<String> testDbConnection() {
        return Mono.usingWhen(
                connectionFactory.create(),  // open connection
                conn -> Mono.just("R2DBC DB Connected successfully! ✔️"), // on success
                Connection::close // close connection safely
        ).onErrorReturn("R2DBC DB Connection FAILED ❌");
    }
}
