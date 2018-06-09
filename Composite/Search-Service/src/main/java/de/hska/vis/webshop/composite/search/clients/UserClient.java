package de.hska.vis.webshop.composite.search.clients;

import de.hska.vis.webshop.core.database.model.IUser;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

// name is the application name from the user core
@FeignClient("user-service")
public interface UserClient {
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    ResponseEntity<List<IUser>> getUserList();
}
