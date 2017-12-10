package de.hska.vis.webshop.core.database.dao.impl;

import de.hska.vis.webshop.core.database.dao.IRoleDAO;
import de.hska.vis.webshop.core.database.model.IRole;
import de.hska.vis.webshop.core.database.model.impl.Role;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class RoleDAO extends GenericHibernateDAO<IRole, Integer> implements IRoleDAO {

    @Override
    @SuppressWarnings("unchecked")
    public Role getRoleByLevel(int roleLevel) {
        Role role = null;
        Session session = getCurrentSession();
        try {
            session.beginTransaction();
            Criteria crit = session.createCriteria(Role.class);
            crit.add(Restrictions.eq("level", roleLevel));
            List<Role> resultList = crit.list();

            if (resultList.size() > 0) {
                role = resultList.get(0);
            }
            session.getTransaction().commit();
            return role;
        } catch (HibernateException e) {
            System.out.println("Hibernate Exception" + e.getMessage());
            session.getTransaction().rollback();
            throw new RuntimeException(e);
        }
    }
}
