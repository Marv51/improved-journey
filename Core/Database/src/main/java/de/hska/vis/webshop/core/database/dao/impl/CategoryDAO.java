package de.hska.vis.webshop.core.database.dao.impl;

import de.hska.vis.webshop.core.database.dao.ICategoryDAO;
import de.hska.vis.webshop.core.database.model.ICategory;
import de.hska.vis.webshop.core.database.model.impl.Category;

public class CategoryDAO extends GenericHibernateDAO<Category, Integer> implements ICategoryDAO {}
