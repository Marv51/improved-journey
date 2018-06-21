package de.hska.vis.webshop.composite.search;

import de.hska.vis.webshop.composite.search.clients.CategoryClient;
import de.hska.vis.webshop.composite.search.clients.ProductClient;
import de.hska.vis.webshop.composite.search.clients.UserClient;
import de.hska.vis.webshop.core.database.model.ICategory;
import de.hska.vis.webshop.core.database.model.IProduct;
import de.hska.vis.webshop.core.database.model.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SearchController {

    @Autowired
    UserClient userClient;
    @Autowired
    ProductClient productClient;
    @Autowired
    CategoryClient categoryClient;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<List<IUser>> getUserList() {
        return new ResponseEntity<>(userClient.getUserList().getBody(), HttpStatus.OK);
    }

    @RequestMapping(value = "/product", method = RequestMethod.GET)
    public ResponseEntity<List<IProduct>> getProductList() {
        return new ResponseEntity<>(productClient.getProductList().getBody(), HttpStatus.OK);
    }

    @RequestMapping(value = "/category", method = RequestMethod.GET)
    public ResponseEntity<List<ICategory>> getCategoryList() {
        return new ResponseEntity<>(categoryClient.getCategoryList().getBody(), HttpStatus.OK);
    }
}