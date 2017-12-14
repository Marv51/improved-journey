package de.hska.vis.webshop.core.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserService {

    public static void main(String[] args) {
    	UserService us = new UserService();
    	us.waitSomeSecs();
        SpringApplication.run(UserService.class, args);
    }
    
    
    private synchronized void waitSomeSecs() {
    	try {
			wait(15 * 1000);
		} catch (InterruptedException ignored) {}
    }
}
