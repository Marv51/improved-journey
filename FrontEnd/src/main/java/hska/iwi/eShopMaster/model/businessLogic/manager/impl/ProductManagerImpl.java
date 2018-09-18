package hska.iwi.eShopMaster.model.businessLogic.manager.impl;

import de.hska.vis.webshop.core.database.model.ICategory;
import de.hska.vis.webshop.core.database.model.IProduct;
import de.hska.vis.webshop.core.database.model.impl.Category;
import de.hska.vis.webshop.core.database.model.impl.Product;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import hska.iwi.eShopMaster.clients.ProductClient;
import hska.iwi.eShopMaster.model.businessLogic.manager.ProductManager;
import org.springframework.cloud.netflix.feign.support.ResponseEntityDecoder;
import org.springframework.http.ResponseEntity;

import javax.validation.constraints.NotNull;
import java.util.List;

public class ProductManagerImpl implements ProductManager {
    private ProductClient helper;

    public ProductManagerImpl() {
        helper = Feign.builder().decoder(new ResponseEntityDecoder(new JacksonDecoder())).encoder(new JacksonEncoder())
                .target(ProductClient.class, "http://zuul:8081");
    }

    public List<IProduct> getProducts() {
        return helper.getProductList().getBody();
    }

    public List<IProduct> getProductsForSearchValues(String searchDescription,
                                                     Integer searchMinPrice, Integer searchMaxPrice) {
        ResponseEntity<List<IProduct>> productResponse = helper.getProductListByCriteria(searchDescription,
                searchMinPrice, searchMaxPrice);
        return productResponse.getBody();
    }

    public IProduct getProductById(int id) {
        return helper.getProductById(id).getBody();
    }


    public boolean addProduct(String name, double price, @NotNull ICategory category, String details) {
        IProduct product;
        if (details == null) {
            product = new Product(name, price, (Category) category);
        } else {
            product = new Product(name, price, (Category) category, details);
        }

        ResponseEntity<Void> response = helper.saveProduct(product);
        return response.getStatusCode().is2xxSuccessful();
    }


    public void deleteProductById(int id) {
        helper.deleteProductById(id);
    }
}
