package de.hska.vis.webshop.core.database.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.hska.vis.webshop.core.database.model.deserializer.ProductDeserializer;
import de.hska.vis.webshop.core.database.model.impl.Category;

@JsonDeserialize(using = ProductDeserializer.class)
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
