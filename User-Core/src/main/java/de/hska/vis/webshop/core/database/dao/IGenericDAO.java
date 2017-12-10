package de.hska.vis.webshop.core.database.dao;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;


/**
 * A generic DAO interface definition. This interface should be extended even if
 * the new interface will add no additional functions.
 *
 * @param <E>  The class of the pojo being persisted.
 * @param <PK> the class of the pojo's id property.
 * @author knad0001
 */
@Repository
public interface IGenericDAO<E, PK extends Serializable> {

    String ASCENDING_SORTING = "asc";
    String DESCENDING_SORTING = "desc";

    Session getCurrentSession();

    /**
     * Get the object with the id specified.
     *
     * @param id the id of the instance to retrieve.
     * @return the instance with the given id.
     */
    E getObjectById(PK id);

    /**
     * Get the object with the name specified, property "name" must exist
     *
     * @param name the name of the instance to retrieve.
     * @return the instance with the given name.
     */
    E getObjectByName(String name);

    /**
     * Get all instances of this entity that have been persisted.
     *
     * @return a list of all instances
     */
    List<E> getObjectList();

    /**
     * Get all instances that match the properties that are set in the given
     * object using a standard Query by Example method.
     *
     * @param entity the example entity
     * @return a list of entities that match the example.
     */
    List<E> get(E entity);

    /**
     * Get all instances of this entity that have been persisted.
     *
     * @param sortOrder    either {@link #ASCENDING_SORTING}
     *                     or {@link #DESCENDING_SORTING}
     * @param sortProperty the key property after which should be sorted
     * @return a list of all instances, sorted by sortProperty in sortOrder
     */
    List<E> getSortedList(String sortOrder, String sortProperty);

    /**
     * Persist the given entity.
     *
     * @param entity the entity to persist.
     * @return true if worked, false if something was wrong
     */
    boolean saveObject(E entity);

    /**
     * Delete the entity passed. same as remove(t.<idPropertyGetter>())
     *
     * @param entity the object to remove.
     * @return true if worked, false if something was wrong
     */
    boolean deleteObject(E entity);

    /**
     * Delete the entity passed. same as remove(t.<idPropertyGetter>())
     *
     * @param id of the object to remove.
     * @return true if worked, false if something was wrong
     */
    boolean deleteById(PK id);

    /**
     * update the entity passed.
     *
     * @param entity the object to update.
     * @return true if worked, false if something was wrong
     */
    boolean updateObject(E entity);
}
