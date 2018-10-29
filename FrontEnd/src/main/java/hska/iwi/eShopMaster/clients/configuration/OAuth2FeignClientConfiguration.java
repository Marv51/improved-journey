package hska.iwi.eShopMaster.clients.configuration;

import feign.Logger;
import feign.RequestInterceptor;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;

import java.util.Collections;

public class OAuth2FeignClientConfiguration {
    @Bean
    public static RequestInterceptor oauth2FeignRequestInterceptor() {
        return new OAuth2FeignRequestInterceptor(new DefaultOAuth2ClientContext(), resource());
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    private static OAuth2ProtectedResourceDetails resource() {
        ResourceOwnerPasswordResourceDetails resourceDetails = new ResourceOwnerPasswordResourceDetails();
        resourceDetails.setUsername("special");
        resourceDetails.setPassword("special");
        resourceDetails.setAccessTokenUri("http://auth:8080/uaa/oauth/token");
        resourceDetails.setClientId("acme");
        resourceDetails.setClientSecret("acmesecret");
        resourceDetails.setGrantType("password");
        resourceDetails.setScope(Collections.singletonList("openid"));
        return resourceDetails;
    }
}
