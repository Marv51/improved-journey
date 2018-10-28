package de.hska.vis.webshop.auth.clients;

import de.hska.vis.webshop.auth.clients.configuration.OAuth2FeignClientConfiguration;
import de.hska.vis.webshop.core.database.model.IUser;
import feign.Param;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "user-service", configuration = OAuth2FeignClientConfiguration.class)
public interface UserClient {
    @RequestMapping(value = "/users/{name}", method = RequestMethod.GET)
    ResponseEntity<IUser> getUserByUsername(@Param("name") String name);
}
