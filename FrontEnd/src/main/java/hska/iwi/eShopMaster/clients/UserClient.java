package hska.iwi.eShopMaster.clients;

import de.hska.vis.webshop.core.database.model.IUser;
import feign.RequestLine;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;

import java.util.List;

@FeignClient(value = "user-service")
public interface UserClient {
    @RequestLine("GET /users")
    ResponseEntity<List<IUser>> getUserList();
}
