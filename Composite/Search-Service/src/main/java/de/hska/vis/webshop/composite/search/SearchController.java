package de.hska.vis.webshop.composite.search;

import de.hska.vis.webshop.core.database.model.ICategory;
import de.hska.vis.webshop.core.database.model.IProduct;
import de.hska.vis.webshop.core.database.model.impl.Category;
import de.hska.vis.webshop.core.database.model.impl.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {

    @RequestMapping(value = "/search/product", method = RequestMethod.GET)
    public ResponseEntity<IProduct> searchProduct(@RequestParam(value = "name") String name,
                                                  @RequestParam(value = "minPrice", required = false) int minPrice,
                                                  @RequestParam(value = "maxPrice", required = false) int maxPrice) {
        return new ResponseEntity<>(new Product(), HttpStatus.I_AM_A_TEAPOT);
    }

    @RequestMapping(value = "/search/category", method = RequestMethod.GET)
    public ResponseEntity<ICategory> searchCategory(@RequestParam("name") String name) {
        return new ResponseEntity<>(new Category(), HttpStatus.I_AM_A_TEAPOT);
    }
}
