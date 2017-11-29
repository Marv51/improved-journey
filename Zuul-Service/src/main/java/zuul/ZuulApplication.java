package zuul;


@SpringBootApplication
@EnableZuulServer
public class ZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZuulApplication.class, args);
	}

}
