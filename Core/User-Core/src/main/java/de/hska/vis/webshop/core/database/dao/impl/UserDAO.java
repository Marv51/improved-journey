package de.hska.vis.webshop.core.database.dao.impl;

import de.hska.vis.webshop.core.database.dao.IUserDAO;
import de.hska.vis.webshop.core.database.model.IUser;
import de.hska.vis.webshop.core.database.model.impl.User;
import de.hska.vis.webshop.core.database.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class UserDAO extends GenericHibernateDAO<IUser, Integer> implements IUserDAO {

    @Override
    public User getUserByUsername(String name) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        try {
            User user = null;
            session.beginTransaction();
            Criteria crit = session.createCriteria(User.class);
            crit.add(Restrictions.eq("username", name));
            List resultList = crit.list();
            if (resultList.size() > 0) {
                user = (User) crit.list().get(0);
            }
            session.getTransaction().commit();
            return user;
        } catch (HibernateException e) {
            System.out.println("Hibernate Exception" + e.getMessage());
            session.getTransaction().rollback();
            throw new RuntimeException(e);
        }
    }
}
