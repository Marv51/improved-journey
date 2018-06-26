package hska.iwi.eShopMaster.clients;

import de.hska.vis.webshop.core.database.model.IProduct;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;

import java.util.List;

@FeignClient("product-service")
public interface ProductClient {
    @RequestLine("GET /product")
    ResponseEntity<List<IProduct>> getProductList();

    @RequestLine("GET /search/product?name={name}")
    ResponseEntity<IProduct> getProductByName(@Param("name") String name);

    //@RequestLine("GET /search/")
    //ResponseEntity<List<IProduct>> getProductListByCriteria(String searchDescription, Double searchMinPrice, Double searchMaxPrice);

    //void saveObject(IProduct product);

    //void deleteById(int id);

    @RequestLine("GET /product/{id}")
    ResponseEntity<IProduct> getProductById(@Param("id") int id);
}
