package de.hska.vis.webshop.core.product;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import de.hska.vis.webshop.core.database.dao.DaoFactory;
import de.hska.vis.webshop.core.database.dao.IProductDAO;
import de.hska.vis.webshop.core.database.model.IProduct;
import de.hska.vis.webshop.core.database.model.impl.Product;
import de.hska.vis.webshop.core.util.HelperUtility;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class ProductController {

    private IProductDAO dao = DaoFactory.getProductDao();
    private HelperUtility<IProduct, Integer> helper = new HelperUtility<>();

    @HystrixCommand
    @RequestMapping(value = "/product",
            method = RequestMethod.POST,
            consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveProduct(@RequestBody IProduct product) {
        HttpStatus code;
        if (dao.saveObject(product)) {
            code = HttpStatus.OK;
        } else {
            code = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(code);
    }

    @HystrixCommand
    @RequestMapping(value = "/product/{stringId}", method = RequestMethod.GET)
    public ResponseEntity<IProduct> getProductById(@PathVariable String stringId) {
        return helper.getResponse(stringId, dao, Integer::parseInt);
    }

    @HystrixCommand
    @RequestMapping(value = "/product/{stringId}", method = RequestMethod.PUT)
    public ResponseEntity<IProduct> updateProduct(@PathVariable String stringId, Product product) {
        HttpStatus code = HttpStatus.OK;
        try {
            Integer parsedId = Integer.parseInt(stringId);
            IProduct savedProduct = dao.getObjectById(parsedId);
            if (product == null) {
                code = HttpStatus.NOT_FOUND;
            } else {
                savedProduct.setName(product.getName());
                savedProduct.setPrice(product.getPrice());
                savedProduct.setCategory(product.getCategory());
                savedProduct.setDetails(product.getDetails());
                if (dao.updateObject(savedProduct)) {
                    // debugging code if something goes wrong while saving
                    code = HttpStatus.I_AM_A_TEAPOT;
                }
            }
        } catch (NumberFormatException ex) {
            // invalid number supplied; returning null and HTTP/400
            product = null;
            code = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(product, code);
    }

    @HystrixCommand
    @RequestMapping(value = "/product/{stringId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteProductById(@PathVariable String stringId) {
        HttpStatus code;
        try {
            int parsedId = Integer.parseInt(stringId);
            IProduct entity = new Product();
            entity.setId(parsedId);
            if (dao.deleteObject(entity)) {
                code = HttpStatus.OK;
            } else {
                code = HttpStatus.NOT_FOUND;
            }
        } catch (NumberFormatException ex) {
            // invalid number supplied; returning null and HTTP/400
            code = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(code);
    }

    @HystrixCommand
    @RequestMapping(value = "/product", method = RequestMethod.GET)
    public ResponseEntity<List<IProduct>> getProductList() {
        return new ResponseEntity<>(dao.getObjectList(), HttpStatus.OK);
    }
}
