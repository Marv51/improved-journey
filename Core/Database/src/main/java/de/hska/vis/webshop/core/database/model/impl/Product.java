package de.hska.vis.webshop.core.database.model.impl;


import com.fasterxml.jackson.annotation.JsonIgnore;
import de.hska.vis.webshop.core.database.model.IProduct;

import javax.persistence.*;

/**
 * This class contains details about products.
 */
@Entity
@Table(name = "product")
public class Product implements IProduct, java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private double price;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "details")
    private String details;

    public Product() {
        // as default value, a value that is negative as signal that it's not saved in the database
        this.id = -1;
    }

    public Product(String name, double price, Category category) {
        this();
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public Product(String name, double price, Category category, String details) {
        this();
        this.name = name;
        this.price = price;
        this.category = category;
        this.details = details;
    }

    public Product(int id, String name, double price, String details) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.details = details;
    }

    public Product(int id, String name, double price, Category category, String details) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.details = details;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public double getPrice() {
        return this.price;
    }

    @Override
    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public Category getCategory() {
        return this.category;
    }

    @Override
    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String getDetails() {
        return this.details;
    }

    @Override
    public void setDetails(String details) {
        this.details = details;
    }
}
