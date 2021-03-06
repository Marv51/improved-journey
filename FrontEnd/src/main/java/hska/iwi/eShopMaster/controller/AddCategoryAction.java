package hska.iwi.eShopMaster.controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import de.hska.vis.webshop.core.database.model.ICategory;
import de.hska.vis.webshop.core.database.model.impl.User;
import hska.iwi.eShopMaster.model.businessLogic.manager.CategoryManager;
import hska.iwi.eShopMaster.model.businessLogic.manager.impl.CategoryManagerImpl;

import java.util.List;
import java.util.Map;

public class AddCategoryAction extends ActionSupport {
    private static final long serialVersionUID = -6704600867133294378L;
    private String newCatName = null;
    private List<ICategory> categories;
    User user;

    public String execute() throws Exception {
        String res = "input";
        Map<String, Object> session = ActionContext.getContext().getSession();
        user = (User) session.get("webshop_user");
        if (user != null && (user.getRole().getTyp().equalsIgnoreCase("admin"))) {
            CategoryManager categoryManager = new CategoryManagerImpl((String) session.get("WebShopAccessToken"));
            // Add category
            categoryManager.addCategory(newCatName);

            // Go and get new Category list
            this.setCategories(categoryManager.getCategories());
            res = "success";
        }
        return res;
    }

    @Override
    public void validate() {
        if (getNewCatName().length() == 0) {
            addActionError(getText("error.catname.required"));
        }
        // Go and get new Category list
        Map<String, Object> session = ActionContext.getContext().getSession();
        CategoryManager categoryManager = new CategoryManagerImpl((String) session.get("WebShopAccessToken"));
        this.setCategories(categoryManager.getCategories());
    }

    public List<ICategory> getCategories() {
        return categories;
    }

    public void setCategories(List<ICategory> categories) {
        this.categories = categories;
    }

    public String getNewCatName() {
        return newCatName;
    }

    public void setNewCatName(String newCatName) {
        this.newCatName = newCatName;
    }
}
