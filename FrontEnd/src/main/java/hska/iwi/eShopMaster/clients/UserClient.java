package hska.iwi.eShopMaster.clients;

import de.hska.vis.webshop.core.database.model.IUser;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface UserClient {
    @RequestLine("GET /users")
    ResponseEntity<List<IUser>> getUserList();

    @RequestLine("GET /users/{name}")
    ResponseEntity<IUser> getUserByUsername(@Param("name") String name);

    @RequestLine("GET /users/id/{id}")
    ResponseEntity<IUser> getUserByUserName(@Param("id") int id);
}
