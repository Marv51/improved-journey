package de.hska.vis.webshop.core.database.model;

import de.hska.vis.webshop.core.database.model.impl.Product;

import java.util.Set;

public interface ICategory {
    int getId();

    void setId(int id);

    String getName();

    void setName(String name);

    Set<Product> getProducts();

    void setProducts(Set<Product> products);
}
