package hska.iwi.eShopMaster.clients;

import de.hska.vis.webshop.core.database.model.ICategory;
import de.hska.vis.webshop.core.database.model.impl.Category;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public interface CategoryClient {
    @RequestLine("GET /category")
    ResponseEntity<List<ICategory>> getCategoryList();

    @RequestLine("DELETE /category/{id}")
    void deleteById(@Param("id") int id);

    @RequestLine("POST /category")
    @Headers("Content-Type: application/json")
    void createCategory(@RequestBody ICategory cat);
}
