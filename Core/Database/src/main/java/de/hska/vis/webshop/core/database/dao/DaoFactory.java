package de.hska.vis.webshop.core.database.dao;

import de.hska.vis.webshop.core.database.dao.impl.CategoryDAO;
import de.hska.vis.webshop.core.database.dao.impl.ProductDAO;
import de.hska.vis.webshop.core.database.dao.impl.RoleDAO;
import de.hska.vis.webshop.core.database.dao.impl.UserDAO;

public class DaoFactory {
    private DaoFactory() {}

    public static CategoryDAO getCategoryDao() {
        return new CategoryDAO();
    }

    public static IProductDAO getProductDao() {
        return new ProductDAO();
    }

    public static IRoleDAO getRoleDao() {
        return new RoleDAO();
    }

    public static IUserDAO getUserDao() {
        return new UserDAO();
    }
}
