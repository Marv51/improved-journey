package de.hska.vis.webshop.core.database.model.impl;

import de.hska.vis.webshop.core.database.model.ICategory;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * This class contains details about categories.
 */
@Entity
@Table(name = "category")
public class Category implements ICategory, java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL,
               mappedBy = "category",
               fetch = FetchType.EAGER)
    private Set<Product> products = new HashSet<>(0);

    public Category() {
        // as default value, a value that is negative as signal that it's not saved in the database
        this.id = -1;
    }

    public Category(String name) {
        this();
        this.name = name;
    }

    public Category(String name, Set<Product> products) {
        this();
        this.name = name;
        this.products = products;
    }

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Category(int id, String name, Set<Product> products) {
        this.id = id;
        this.name = name;
        this.products = products;
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
    public Set<Product> getProducts() {
        return this.products;
    }

    @Override
    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
