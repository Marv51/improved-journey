package de.hska.vis.webshop.hystrix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrixDashboard
public class HystrixDashboardApplication {

    public static void main(String[] args) {
        try {
            Thread.sleep(30 * 1000);
        } catch (InterruptedException ignored) {}
        SpringApplication.run(HystrixDashboardApplication.class, args);
    }
}