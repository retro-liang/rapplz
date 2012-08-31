package com.retro.rapplz.db.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.retro.rapplz.db.entity.Category;

@Repository
public class CategoryDaoImpl implements CategoryDao
{
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Category getCategory(Long id)
	{
		return (Category)sessionFactory.getCurrentSession().load(Category.class, id);
	}
	
	@Override
	public Category getCategoryByName(String name)
	{
		return (Category)sessionFactory.getCurrentSession().createQuery("select c from Category c where c.name like '" + name + "'").setCacheable(true).uniqueResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Category> getCategories()
	{
		return sessionFactory.getCurrentSession().createQuery("from Category").list();		
	}

	@Override
	public void save(Category category)
	{
		sessionFactory.getCurrentSession().save(category);
	}

	@Override
	public void remove(Long id)
	{
		sessionFactory.getCurrentSession().delete(id);
	}
}