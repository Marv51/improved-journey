

@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableHystrixDashboard
@RibbonClient("search-service")
public class SearchService {
	
	public static void main(String[] args) {
		SpringApplication.run(SearchService.class, args);
	}
}
