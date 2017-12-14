package de.hska.vis.webshop.core.database.model;

import de.hska.vis.webshop.core.database.model.impl.Category;

public interface IProduct {
    int getId();

    void setId(int id);

    String getName();

    void setName(String name);

    double getPrice();

    void setPrice(double price);

    Category getCategory();

    void setCategory(Category category);

    String getDetails();

    void setDetails(String details);
}
