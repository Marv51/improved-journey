package hska.iwi.eShopMaster.controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import de.hska.vis.webshop.core.database.model.ICategory;
import de.hska.vis.webshop.core.database.model.IProduct;
import de.hska.vis.webshop.core.database.model.impl.Category;
import de.hska.vis.webshop.core.database.model.impl.User;
import hska.iwi.eShopMaster.model.businessLogic.manager.CategoryManager;
import hska.iwi.eShopMaster.model.businessLogic.manager.impl.CategoryManagerImpl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ListAllProductsAction extends ActionSupport {
    private static final long serialVersionUID = -94109228677381902L;

    User user;
    private List<IProduct> products;

    public String execute() throws Exception {
        String result = "input";

        Map<String, Object> session = ActionContext.getContext().getSession();
        user = (User) session.get("webshop_user");

        if (user != null) {
            System.out.println("list all products!");
            CategoryManager categoryManager = new CategoryManagerImpl((String) session.get("WebShopAccessToken"));
            List<ICategory> categories = categoryManager.getCategories();
            this.products = transformList(categories);
            result = "success";
        }

        return result;
    }

    private List<IProduct> transformList(List<ICategory> categories) {
        List<IProduct> products = new LinkedList<>();
        for (ICategory c : categories) {
            for (IProduct p : c.getProducts()) {
                Category cat = new Category(c.getId(), c.getName());
                p.setCategory(cat);
                products.add(p);
            }
        }
        return products;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<IProduct> getProducts() {
        return products;
    }

    public void setProducts(List<IProduct> products) {
        this.products = products;
    }

}
