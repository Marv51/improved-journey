package hska.iwi.eShopMaster.clients.configuration;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class WebshopFeignRequestInterceptor implements RequestInterceptor {

    private String token;

    public WebshopFeignRequestInterceptor(String token){
        this.token = token;
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("Authorization", "Bearer " + token);
    }
}
