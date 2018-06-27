package hska.iwi.eShopMaster.model.businessLogic.manager.impl;


import de.hska.vis.webshop.core.database.model.ICategory;
import de.hska.vis.webshop.core.database.model.impl.Category;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import hska.iwi.eShopMaster.clients.CategoryClient;
import hska.iwi.eShopMaster.model.businessLogic.manager.CategoryManager;
import org.springframework.cloud.netflix.feign.support.ResponseEntityDecoder;
import java.util.List;

public class CategoryManagerImpl implements CategoryManager{
	private CategoryClient helper;
	
	public CategoryManagerImpl() {
		helper = Feign.builder().decoder(new ResponseEntityDecoder(new JacksonDecoder()))
                .encoder(new JacksonEncoder())
				.target(CategoryClient.class, "http://zuul:8081");
	}

	public List<ICategory> getCategories() {
		return helper.getCategoryList().getBody();
	}


	public void addCategory(String name) {
		Category cat = new Category(name);
		helper.createCategory(cat);
	}

	public void delCategoryById(int id) {
		helper.deleteById(id);
	}
}
