package hska.iwi.eShopMaster.model.businessLogic.manager;

import de.hska.vis.webshop.core.database.model.ICategory;
import de.hska.vis.webshop.core.database.model.IProduct;

import java.util.List;

public interface ProductManager {
    List<IProduct> getProducts();

    IProduct getProductById(int id);

    boolean addProduct(String name, double price, ICategory categoryId, String details);

    List<IProduct> getProductsForSearchValues(String searchValue, Integer searchMinPrice, Integer searchMaxPrice);

    void deleteProductById(int id);
}
