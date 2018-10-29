package hska.iwi.eShopMaster.controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import de.hska.vis.webshop.core.database.model.ICategory;
import de.hska.vis.webshop.core.database.model.impl.User;
import hska.iwi.eShopMaster.model.businessLogic.manager.CategoryManager;
import hska.iwi.eShopMaster.model.businessLogic.manager.impl.CategoryManagerImpl;

import java.util.List;
import java.util.Map;

public class DeleteCategoryAction extends ActionSupport {
    private static final long serialVersionUID = 1254575994729199914L;
    private int catId;
    private List<ICategory> categories;

    public String execute() throws Exception {
        String res = "input";
        Map<String, Object> session = ActionContext.getContext().getSession();
        User user = (User) session.get("webshop_user");

        if (user != null && (user.getRole().getTyp().equalsIgnoreCase("admin"))) {
            // Helper inserts new Category in DB:
            CategoryManager categoryManager = new CategoryManagerImpl((String)session.get("WebShopAccessToken"));
            categoryManager.delCategoryById(catId);
            categories = categoryManager.getCategories();
            res = "success";
        }

        return res;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public List<ICategory> getCategories() {
        return categories;
    }

    public void setCategories(List<ICategory> categories) {
        this.categories = categories;
    }
}
