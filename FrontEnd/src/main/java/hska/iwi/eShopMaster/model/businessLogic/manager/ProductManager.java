package hska.iwi.eShopMaster.model.businessLogic.manager;

import de.hska.vis.webshop.core.database.model.IProduct;

import java.util.List;

public interface ProductManager {

	public List<IProduct> getProducts();

	public IProduct getProductById(int id);

	//public IProduct getProductByName(String name);

	public int addProduct(String name, double price, int categoryId, String details);

	public List<IProduct> getProductsForSearchValues(String searchValue, Double searchMinPrice, Double searchMaxPrice);
	
	//public boolean deleteProductsByCategoryId(int categoryId);
	
    public void deleteProductById(int id);
    
	
}
