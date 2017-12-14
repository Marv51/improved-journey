package de.hska.vis.webshop.core.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserService {

    public static void main(String[] args) {
        try {
            Thread.sleep(15 * 1000);
        } catch (InterruptedException ignored) {}
        SpringApplication.run(UserService.class, args);
    }
}
