package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner initData(CustomerCarService customerCarService) {  // ← Use INTERFACE here too!
//        return args -> {
//            Flux.just(
//                            new CustomerCar(null, "123456", null),
//                            new CustomerCar(null, "654321", null),
//                            new CustomerCar(null, "101010", null),
//                            new CustomerCar(null, "010101", null),
//                            new CustomerCar(null, "ABC123", null),
//                            new CustomerCar(null, "XYZ789", null)
//                    )
//                    .flatMap(customerCarService::createCustomerCar)  // ← Uses interface method
//                    .subscribe(
//                            car -> System.out.println("✓ Saved customer car with VIN: " + car.getVin()),
//                            error -> System.err.println("✗ Error: " + error.getMessage()),
//                            () -> System.out.println("Initial data loading completed!")
//                    );
//        };
//    }
}
