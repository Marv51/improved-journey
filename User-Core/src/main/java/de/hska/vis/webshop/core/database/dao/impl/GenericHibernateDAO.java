package de.hska.vis.webshop.core.database.dao.impl;

import com.sun.istack.internal.Nullable;
import de.hska.vis.webshop.core.database.dao.IGenericDAO;
import de.hska.vis.webshop.core.database.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class GenericHibernateDAO<E, PK extends Serializable> implements IGenericDAO<E, PK> {
    /**
     * The class of the pojo being persisted.
     */
    private Class<E> entityClass;

    @SuppressWarnings("unchecked")
    GenericHibernateDAO() {
        final Type thisType = getClass().getGenericSuperclass();
        final Type type;
        if (thisType instanceof ParameterizedType) {
            type = ((ParameterizedType) thisType).getActualTypeArguments()[0];
        } else if (thisType instanceof Class) {
            type = ((ParameterizedType) ((Class) thisType).getGenericSuperclass()).getActualTypeArguments()[0];
        } else {
            throw new IllegalArgumentException("Problem handling type construction for " + getClass());
        }

        if (type instanceof Class) {
            this.entityClass = (Class<E>) type;
        } else if (type instanceof ParameterizedType) {
            this.entityClass = (Class<E>) ((ParameterizedType) type).getRawType();
        } else {
            throw new IllegalArgumentException("Problem determining the class of the generic for " + getClass());
        }

    }

    @Override
    public boolean saveObject(E entity) {
        Session session = getCurrentSession();
        try {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return false;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public E getObjectById(PK id) {
        Session session = getCurrentSession();

        session.beginTransaction();
        E entity = session.get(entityClass, id);
        session.getTransaction().commit();
        return entity;
    }

    @Override
    @SuppressWarnings("unchecked")
    @Nullable
    public E getObjectByName(String name) {
        Session session = getCurrentSession();
        try {
            E entity = null;
            session.beginTransaction();
            Criteria crit = session.createCriteria(entityClass);
            crit.add(Restrictions.eq("name", name));
            List<E> resultList = crit.list();
            if (resultList.size() > 0) {
                entity = (E) crit.list().get(0);
            }
            session.getTransaction().commit();
            return entity;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return null;
        }
    }

    @Override
    public boolean deleteObject(E entity) {
        Session session = getCurrentSession();

        try {
            session.beginTransaction();
            session.delete(entity);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return false;
        }
    }

    @Override
    public boolean deleteById(PK id) {
        Session session = getCurrentSession();

        try {
            session.beginTransaction();
            E entity = session.get(entityClass, id);
            session.delete(entity);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException e) {
            //log.error("Hibernate Exception" + e.getMessage());
            session.getTransaction().rollback();
            return false;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    @Nullable
    public List<E> get(E entity) {
        Session session = getCurrentSession();
        try {
            session.beginTransaction();
            Criteria crit = session.createCriteria(entityClass);
            crit.add(Restrictions.idEq(entity));
            List<E> resultList = crit.list();
            session.getTransaction().commit();
            return resultList;
        } catch (HibernateException e) {
            //log.error("Hibernate Exception" + e.getMessage());
            session.getTransaction().rollback();
            return null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    @Nullable
    public List<E> getObjectList() {
        Session session = getCurrentSession();

        try {
            session.beginTransaction();

            Criteria crit = session.createCriteria(entityClass);
            List<E> resultList = crit.list();
            session.getTransaction().commit();
            return resultList;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    @Nullable
    public List<E> getSortedList(String sortOrder, String sortProperty) {
        Session session = getCurrentSession();

        try {
            session.beginTransaction();

            Criteria crit = session.createCriteria(entityClass);
            if (!"".equals(sortProperty)) {
                if (sortOrder.equals(ASCENDING_SORTING)) {
                    crit.addOrder(Order.asc(sortProperty));
                } else if (sortOrder.equals(DESCENDING_SORTING)) {
                    crit.addOrder(Order.desc(sortProperty));
                }
            }
            List<E> resultList = crit.list();
            session.getTransaction().commit();
            return resultList;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return null;
        }
    }

    @Override
    public boolean updateObject(E entity) {
        Session session = getCurrentSession();

        try {
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return false;
        }
    }

    @Override
    public Session getCurrentSession() {
        return HibernateUtil.getSessionFactory().getCurrentSession();
    }
}
