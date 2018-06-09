package de.hska.vis.webshop.core.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

import static de.hska.vis.webshop.core.util.StarterUtility.delayCore;


@SpringBootApplication
@EnableCircuitBreaker
public class UserService {

    public static void main(String[] args) {
        delayCore();
        SpringApplication.run(UserService.class, args);
    }
}
