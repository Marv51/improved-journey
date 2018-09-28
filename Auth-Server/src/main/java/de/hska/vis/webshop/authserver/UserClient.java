package de.hska.vis.webshop.authserver;

import de.hska.vis.webshop.authserver.exception.UserNotExist;
import de.hska.vis.webshop.core.database.model.IUser;
import feign.Param;
import feign.RequestLine;
import org.springframework.http.ResponseEntity;


public interface UserClient {
    @RequestLine("GET /users/{name}")
    ResponseEntity<IUser> getUserByUsername(@Param("name") String name) throws UserNotExist;
}
