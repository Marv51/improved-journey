package de.hska.vis.webshop.composite.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableHystrix
@EnableHystrixDashboard
@RibbonClient(name = "search-service")
@EnableFeignClients
public class SearchServiceApplication {

    public static void main(String[] args) {
        try {
            // given the 30s runup time from the cores
            // no need to start at the same time as them
            Thread.sleep(60 * 1000);
        } catch (InterruptedException ignored) {}
        SpringApplication.run(SearchServiceApplication.class, args);
    }
}
