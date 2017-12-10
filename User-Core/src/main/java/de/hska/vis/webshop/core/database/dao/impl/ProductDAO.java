package de.hska.vis.webshop.core.database.dao.impl;

import de.hska.vis.webshop.core.database.dao.IProductDAO;
import de.hska.vis.webshop.core.database.model.IProduct;
import de.hska.vis.webshop.core.database.model.impl.Product;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class ProductDAO extends GenericHibernateDAO<IProduct, Integer> implements IProductDAO {

    @Override
    @SuppressWarnings("unchecked")
    public List<IProduct> getProductListByCriteria(String searchDescription,
                                                   Double searchMinPrice, Double searchMaxPrice) {
        Session session = getCurrentSession();
        Transaction transaction = null;
        List<IProduct> productList = null;

        try {
            transaction = session.beginTransaction();
            Criteria crit = session.createCriteria(Product.class);

            // Define Search HQL:
            if (searchDescription != null && searchDescription.length() > 0) {    // searchValue is set:
                searchDescription = "%" + searchDescription + "%";
                crit.add(Restrictions.ilike("details", searchDescription));
            }

            if (searchMinPrice != null && searchMaxPrice != null) {
                crit.add(Restrictions.between("price", searchMinPrice, searchMaxPrice));
            } else if (searchMinPrice != null) {
                crit.add(Restrictions.ge("price", searchMinPrice));
            } else {
                crit.add(Restrictions.le("price", searchMaxPrice));
            }

            productList = crit.list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return productList;
    }


}
