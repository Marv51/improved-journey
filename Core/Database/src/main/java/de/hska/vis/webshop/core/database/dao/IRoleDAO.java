package de.hska.vis.webshop.core.database.dao;

import de.hska.vis.webshop.core.database.model.IRole;

public interface IRoleDAO extends IGenericDAO<IRole, Integer> {
    IRole getRoleByLevel(int roleLevel);
}
