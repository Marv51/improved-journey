package de.hska.vis.webshop.hystrix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrixDashboard
@RestController
public class HystrixDashboardApplication {

    public static void main(String[] args) {
        try {
            Thread.sleep(30 * 1000);
        } catch (InterruptedException ignored) {}
        SpringApplication.run(HystrixDashboardApplication.class, args);
    }

    @RequestMapping("/")
	public void home(HttpServletResponse response) throws IOException {
		response.sendRedirect("/hystrix");
	}
}