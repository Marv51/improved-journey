package de.hska.vis.webshop.core.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import static de.hska.vis.webshop.core.util.StarterUtility.delayCore;

@SpringBootApplication
@EnableCircuitBreaker
@EnableResourceServer
public class ProductService {

    public static void main(String[] args) {
        delayCore();
        SpringApplication.run(ProductService.class, args);
    }
}
