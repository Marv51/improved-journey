package hska.iwi.eShopMaster.controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import de.hska.vis.webshop.core.database.model.impl.User;
import hska.iwi.eShopMaster.model.businessLogic.manager.impl.ProductManagerImpl;

import java.util.Map;

public class DeleteProductAction extends ActionSupport {
    private static final long serialVersionUID = 3666796923937616729L;
    private int id;

    public String execute() throws Exception {
        String res = "input";

        Map<String, Object> session = ActionContext.getContext().getSession();
        User user = (User) session.get("webshop_user");

        if (user != null && (user.getRole().getTyp().equalsIgnoreCase("admin"))) {
            new ProductManagerImpl((String) session.get("WebShopAccessToken")).deleteProductById(id);
            res = "success";
        }

        return res;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
