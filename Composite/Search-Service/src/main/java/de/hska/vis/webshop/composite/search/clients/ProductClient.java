package de.hska.vis.webshop.composite.search.clients;

import de.hska.vis.webshop.core.database.model.IProduct;
import de.hska.vis.webshop.core.database.model.impl.Product;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// name is the application name from the product core
@FeignClient("product-service")
public interface ProductClient {
    @RequestMapping(value = "/product", method = RequestMethod.POST)
    ResponseEntity<Void> saveProduct(@RequestBody Product product);

    @RequestMapping(value = "/product/{stringId}", method = RequestMethod.GET)
    ResponseEntity<IProduct> getProductById(@PathVariable("stringId") String id);

    @RequestMapping(value = "/product/{stringId}", method = RequestMethod.PUT)
    ResponseEntity<IProduct> updateProduct(@PathVariable("stringId") String id, @RequestBody Product product);

    @RequestMapping(value = "/product/{stringId}", method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteProductById(@PathVariable("stringId") String id);

    @RequestMapping(value = "/product", method = RequestMethod.GET)
    ResponseEntity<List<IProduct>> getProductList();
}
