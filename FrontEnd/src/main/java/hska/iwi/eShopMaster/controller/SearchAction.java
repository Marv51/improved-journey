package hska.iwi.eShopMaster.controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import de.hska.vis.webshop.core.database.model.ICategory;
import de.hska.vis.webshop.core.database.model.IProduct;
import de.hska.vis.webshop.core.database.model.impl.User;
import hska.iwi.eShopMaster.model.businessLogic.manager.CategoryManager;
import hska.iwi.eShopMaster.model.businessLogic.manager.ProductManager;
import hska.iwi.eShopMaster.model.businessLogic.manager.impl.CategoryManagerImpl;
import hska.iwi.eShopMaster.model.businessLogic.manager.impl.ProductManagerImpl;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public class SearchAction extends ActionSupport {
    private static final long serialVersionUID = -6565401833074694229L;

    private String searchDescription = null;
    private String searchMinPrice;
    private String searchMaxPrice;

    private Integer sMinPrice = null;
    private Integer sMaxPrice = null;

    private User user;
    private List<IProduct> products;
    private List<ICategory> categories;

    public String execute() throws Exception {
        String result = "input";

        // Get user:
        Map<String, Object> session = ActionContext.getContext().getSession();
        user = (User) session.get("webshop_user");
        ActionContext.getContext().setLocale(Locale.US);

        if (user != null) {
            // Search products and show results:
            if (!searchMinPrice.isEmpty()) {
                sMinPrice = Integer.parseInt(this.searchMinPrice);
            }
            if (!searchMaxPrice.isEmpty()) {
                sMaxPrice = Integer.parseInt(this.searchMaxPrice);
            }

            ProductManager productManager = new ProductManagerImpl();
            this.products = productManager.getProductsForSearchValues(this.searchDescription, sMinPrice, sMaxPrice);

            // Show all categories:
            CategoryManager categoryManager = new CategoryManagerImpl();
            this.categories = categoryManager.getCategories();
            result = "success";
        }

        return result;
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

    public List<ICategory> getCategories() {
        return categories;
    }

    public void setCategories(List<ICategory> categories) {
        this.categories = categories;
    }

    public String getSearchValue() {
        return searchDescription;
    }

    public void setSearchValue(String searchValue) {
        this.searchDescription = searchValue;
    }

    public String getSearchMinPrice() {
        return searchMinPrice;
    }

    public void setSearchMinPrice(String searchMinPrice) {
        this.searchMinPrice = searchMinPrice;
    }

    public String getSearchMaxPrice() {
        return searchMaxPrice;
    }

    public void setSearchMaxPrice(String searchMaxPrice) {
        this.searchMaxPrice = searchMaxPrice;
    }
}
