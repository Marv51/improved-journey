package hska.iwi.eShopMaster.model.businessLogic.manager.impl;

import de.hska.vis.webshop.core.database.model.ICategory;
import de.hska.vis.webshop.core.database.model.IProduct;
import de.hska.vis.webshop.core.database.model.impl.Category;
import de.hska.vis.webshop.core.database.model.impl.Product;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import hska.iwi.eShopMaster.clients.ProductClient;
import hska.iwi.eShopMaster.clients.UserClient;
import hska.iwi.eShopMaster.model.businessLogic.manager.CategoryManager;
import hska.iwi.eShopMaster.model.businessLogic.manager.ProductManager;
import org.springframework.cloud.netflix.feign.support.ResponseEntityDecoder;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class ProductManagerImpl implements ProductManager {
	private ProductClient helper;
	
	public ProductManagerImpl() {
		helper = Feign.builder().decoder(new ResponseEntityDecoder(new JacksonDecoder()))
				.target(ProductClient.class, "http://localhost:8080");
	}

	public List<IProduct> getProducts() {
		return helper.getProductList().getBody();
	}
	
	public List<IProduct> getProductsForSearchValues(String searchDescription,
			Double searchMinPrice, Double searchMaxPrice) {
		throw new NotImplementedException();
		//return helper.getProductListByCriteria(searchDescription, searchMinPrice, searchMaxPrice);
	}

	public IProduct getProductById(int id) {
		return helper.getProductById(id).getBody();
	}

	
	public int addProduct(String name, double price, int categoryId, String details) {
		throw new NotImplementedException();
		/*int productId = -1;
		
		CategoryManager categoryManager = new CategoryManagerImpl();
		ICategory category = categoryManager.getCategory(categoryId);
		
		if(category != null){
			IProduct product;
			if(details == null){
				product = new Product(name, price, (Category)category);
			} else{
				product = new Product(name, price, (Category)category, details);
			}
			
			helper.saveObject(product);
			productId = product.getId();
		}
			 
		return productId;
		*/
	}
	

	public void deleteProductById(int id) {
		throw new NotImplementedException();
		//helper.deleteById(id);
	}

}
