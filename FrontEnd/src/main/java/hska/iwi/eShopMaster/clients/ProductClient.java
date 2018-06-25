package hska.iwi.eShopMaster.clients;

import de.hska.vis.webshop.core.database.model.IProduct;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("product-service")
public interface ProductClient {
    @RequestMapping(value = "/product", method = RequestMethod.GET)
    ResponseEntity<List<IProduct>> getProductList();
}
