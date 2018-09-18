package hska.iwi.eShopMaster.clients;

import de.hska.vis.webshop.core.database.model.IProduct;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ProductClient {
    @RequestLine("GET /product")
    ResponseEntity<List<IProduct>> getProductList();

    @RequestLine("GET /search/product?name={name}")
    ResponseEntity<IProduct> getProductByName(@Param("name") String name);

    @RequestLine("GET /search/product?name={name}&minPrice={min}&maxPrice={max}")
    ResponseEntity<List<IProduct>> getProductListByCriteria(@Param("name") String searchDescription,
                                                            @Param("min") Integer searchMinPrice,
                                                            @Param("max") Integer searchMaxPrice);

    @RequestLine("POST /product")
    @Headers("Content-Type: application/json")
    ResponseEntity<Void> saveProduct(@RequestBody IProduct product);

    @RequestLine("GET /product/{id}")
    ResponseEntity<IProduct> getProductById(@Param("id") int id);

    @RequestLine("DELETE /product/{id}")
    void deleteProductById(@Param("id") int id);
}
