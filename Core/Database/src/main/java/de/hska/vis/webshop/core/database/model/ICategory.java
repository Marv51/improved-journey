package de.hska.vis.webshop.core.database.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.hska.vis.webshop.core.database.model.deserializer.CategoryDeserializer;
import de.hska.vis.webshop.core.database.model.impl.Product;

import java.util.Set;

@JsonDeserialize(using = CategoryDeserializer.class)
public interface ICategory {
    int getId();

    void setId(int id);

    String getName();

    void setName(String name);

    Set<Product> getProducts();

    void setProducts(Set<Product> products);
}
