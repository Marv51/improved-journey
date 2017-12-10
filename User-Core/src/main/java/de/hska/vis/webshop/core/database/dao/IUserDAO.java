package de.hska.vis.webshop.core.database.dao;

import de.hska.vis.webshop.core.database.model.IUser;

public interface IUserDAO extends IGenericDAO<IUser, Integer> {

    IUser getUserByUsername(String name);
}
