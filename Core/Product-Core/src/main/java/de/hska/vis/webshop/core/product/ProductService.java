package de.hska.vis.webshop.core.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProductService {

    public static void main(String[] args) {
        try {
            Thread.sleep(30 * 1000);
        } catch (InterruptedException ignored) {}
        SpringApplication.run(ProductService.class, args);
    }
}
