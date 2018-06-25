package hska.iwi.eShopMaster.clients;

import de.hska.vis.webshop.core.database.model.ICategory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("category-service")
public interface CategoryClient {
    @RequestMapping(value = "/category", method = RequestMethod.GET)
    ResponseEntity<List<ICategory>> getCategoryList();
}
