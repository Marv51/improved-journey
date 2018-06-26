package hska.iwi.eShopMaster.model.businessLogic.manager;

import de.hska.vis.webshop.core.database.model.ICategory;
import de.hska.vis.webshop.core.database.model.impl.Category;

import java.util.List;

public interface CategoryManager {

	public List<ICategory> getCategories();
	
	//public ICategory getCategory(int id);
	
	//public ICategory getCategoryByName(String name);
	
	public void addCategory(String name);
	
	//public void delCategory(Category cat);
	
	public void delCategoryById(int id);

	
}
