package hska.iwi.eShopMaster.model.businessLogic.manager.impl;

import de.hska.vis.webshop.core.database.model.IProduct;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import hska.iwi.eShopMaster.clients.ProductClient;
import hska.iwi.eShopMaster.model.businessLogic.manager.ProductManager;
import org.springframework.cloud.netflix.feign.support.ResponseEntityDecoder;

import java.util.List;

public class ProductManagerImpl implements ProductManager {
	private ProductClient helper;
	
	public ProductManagerImpl() {
		helper = Feign.builder().decoder(new ResponseEntityDecoder(new JacksonDecoder()))
				.target(ProductClient.class, "http://zuul:8081");
	}

	public List<IProduct> getProducts() {
		return helper.getProductList().getBody();
	}
	
	public List<IProduct> getProductsForSearchValues(String searchDescription,
			Double searchMinPrice, Double searchMaxPrice) {
		return helper.getProductListByCriteria(searchDescription, searchMinPrice, searchMaxPrice).getBody();
	}

	public IProduct getProductById(int id) {
		return helper.getProductById(id).getBody();
	}

	
	public int addProduct(String name, double price, int categoryId, String details) {
		throw new UnsupportedOperationException();
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
		helper.deleteProductById(id);
	}

}
