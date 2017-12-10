package de.hska.vis.webshop.core.database.dao;

import de.hska.vis.webshop.core.database.model.IProduct;

import java.util.List;

public interface IProductDAO extends IGenericDAO<IProduct, Integer> {
    List<IProduct> getProductListByCriteria(String searchDescription,
                                            Double searchMinPrice, Double searchMaxPrice);
}
