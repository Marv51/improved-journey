package de.hska.vis.webshop.core.category;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CategoryService {

    public static void main(String[] args) {
        SpringApplication.run(CategoryService.class, args);
    }
}
