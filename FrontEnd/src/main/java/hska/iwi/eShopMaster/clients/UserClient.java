package hska.iwi.eShopMaster.clients;

import de.hska.vis.webshop.core.database.model.IUser;
import de.hska.vis.webshop.core.database.model.impl.User;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import hska.iwi.eShopMaster.model.businessLogic.manager.impl.UserManagerImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


public interface UserClient {
    @RequestLine("GET /users")
    ResponseEntity<List<IUser>> getUserList();

    @RequestLine("GET /users/{name}")
    ResponseEntity<IUser> getUserByUsername(@Param("name") String name) throws UserManagerImpl.UserNotExist;

    @RequestLine("GET /users/id/{id}")
    ResponseEntity<IUser> getUserByUserName(@Param("id") int id);

    @RequestLine("POST /users")
    @Headers("Content-Type: application/json")
    ResponseEntity<Void> saveUser(@RequestBody User user);
}
