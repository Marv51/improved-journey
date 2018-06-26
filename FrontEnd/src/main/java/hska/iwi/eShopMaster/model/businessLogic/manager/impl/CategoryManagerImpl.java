package hska.iwi.eShopMaster.model.businessLogic.manager.impl;


import de.hska.vis.webshop.core.database.model.ICategory;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import hska.iwi.eShopMaster.clients.CategoryClient;
import hska.iwi.eShopMaster.model.businessLogic.manager.CategoryManager;
import org.springframework.cloud.netflix.feign.support.ResponseEntityDecoder;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class CategoryManagerImpl implements CategoryManager{
	private CategoryClient helper;
	
	public CategoryManagerImpl() {
		helper = Feign.builder().decoder(new ResponseEntityDecoder(new JacksonDecoder()))
				.target(CategoryClient.class, "http://localhost:8080");
	}

	public List<ICategory> getCategories() {
		return helper.getCategoryList().getBody();
	}


	public void addCategory(String name) {
		throw new NotImplementedException();
		//Category cat = new Category(name);
		//helper.saveObject(cat);

	}

	public void delCategoryById(int id) {
		throw new NotImplementedException();
		//helper.deleteById(id);
	}
}
