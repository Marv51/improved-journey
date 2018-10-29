package hska.iwi.eShopMaster.controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import de.hska.vis.webshop.core.database.model.ICategory;
import de.hska.vis.webshop.core.database.model.impl.User;
import hska.iwi.eShopMaster.model.businessLogic.manager.CategoryManager;
import hska.iwi.eShopMaster.model.businessLogic.manager.ProductManager;
import hska.iwi.eShopMaster.model.businessLogic.manager.impl.CategoryManagerImpl;
import hska.iwi.eShopMaster.model.businessLogic.manager.impl.ProductManagerImpl;

import java.util.List;
import java.util.Map;

public class AddProductAction extends ActionSupport {
    private static final long serialVersionUID = 39979991339088L;

    private String name = null;
    private String price = null;
    private double priceValue = -1;
    private int categoryId = -1;
    private String details = null;
    private ICategory selectedCategory = null;
    private List<ICategory> categories;

    public String execute() throws Exception {
        String result = "input";
        Map<String, Object> session = ActionContext.getContext().getSession();
        User user = (User) session.get("webshop_user");

        if (user != null && (user.getRole().getTyp().equalsIgnoreCase("admin"))) {
            ProductManager productManager = new ProductManagerImpl();
            boolean success = productManager.addProduct(getName(), getPriceValue(), getSelectedCategory(), getDetails());

            if (success) {
                result = "success";
            }
        }

        return result;
    }

    @Override
    public void validate() {
        CategoryManager categoryManager = new CategoryManagerImpl();
        setCategories(categoryManager.getCategories());

        // Validate name:
        if (getName() == null || getName().length() == 0) {
            addActionError(getText("error.product.name.required"));
        }

        // Validate priceString:
        String localPrice = getPrice();
        if (localPrice == null || localPrice.isEmpty()) {
            addActionError(getText("error.product.price.required"));
        } else {
            try {
                double pr = Double.parseDouble(localPrice);
                setPriceValue(pr);
            } catch (NumberFormatException ex) {
                addActionError(getText("error.product.price.regex"));
            }
        }

        // Validate Category
        boolean categoryValid = false;
        for (ICategory category : this.getCategories()) {
            if (category.getId() == this.getCategoryId()) {
                categoryValid = true;
                setSelectedCategory(category);
                break;
            }
        }
        if (!categoryValid) {
            addActionError("Category doesn't exist");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String priceString) {
        this.price = priceString;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public List<ICategory> getCategories() {
        return categories;
    }

    public void setCategories(List<ICategory> categories) {
        this.categories = categories;
    }

    public ICategory getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(ICategory selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    public double getPriceValue() {
        return priceValue;
    }

    public void setPriceValue(double price) {
        this.priceValue = price;
    }
}
