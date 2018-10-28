package hska.iwi.eShopMaster.model.businessLogic.manager;

import de.hska.vis.webshop.core.database.model.ICategory;

import java.util.List;

public interface CategoryManager {
    List<ICategory> getCategories();

    void addCategory(String name);

    void delCategoryById(int id);
}
