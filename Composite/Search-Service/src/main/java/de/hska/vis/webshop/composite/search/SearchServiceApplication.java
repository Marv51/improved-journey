package de.hska.vis.webshop.composite.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

import static de.hska.vis.webshop.core.util.StarterUtility.delayComposite;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableHystrix
@EnableHystrixDashboard
@RibbonClient(name = "search-service")
@EnableFeignClients
public class SearchServiceApplication {

    public static void main(String[] args) {
        delayComposite();
        SpringApplication.run(SearchServiceApplication.class, args);
    }
}
