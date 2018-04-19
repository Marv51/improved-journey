package de.hska.vis.webshop.core.category;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@SpringBootApplication
@EnableCircuitBreaker
public class CategoryService {

    public static void main(String[] args) {
        try {
            Thread.sleep(30 * 1000);
        } catch (InterruptedException ignored) {}
        SpringApplication.run(CategoryService.class, args);
    }
}
