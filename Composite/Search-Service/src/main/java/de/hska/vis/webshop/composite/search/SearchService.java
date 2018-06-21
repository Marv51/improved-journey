package de.hska.vis.webshop.composite.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

import static de.hska.vis.webshop.core.util.StarterUtility.delayComposite;


@SpringBootApplication
@EnableCircuitBreaker
@EnableFeignClients
public class SearchService {

    public static void main(String[] args) {
        delayComposite();
        SpringApplication.run(SearchService.class, args);
    }
}
