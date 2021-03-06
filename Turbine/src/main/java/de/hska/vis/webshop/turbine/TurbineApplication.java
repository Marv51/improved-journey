package de.hska.vis.webshop.turbine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;


@EnableAutoConfiguration
@EnableTurbine
@EnableEurekaClient
@EnableHystrixDashboard
@SpringBootApplication
public class TurbineApplication {

	public static void main(String[] args) {
		
		try {
            Thread.sleep(60 * 1000);
        } catch (InterruptedException ignored) {}
		
		SpringApplication.run(TurbineApplication.class, args);
	}
}