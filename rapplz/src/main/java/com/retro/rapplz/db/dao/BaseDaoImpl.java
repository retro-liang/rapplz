package com.retro.rapplz.db.dao;

import java.util.List;
import java.util.logging.Logger;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseDaoImpl implements BaseDao
{
	private static final Logger logger = Logger.getLogger(BaseDaoImpl.class.getName());
	
	@Autowired
	protected SessionFactory sessionFactory;
	
	@SuppressWarnings("rawtypes")
	@Override
	public Object loadById(Class clazz, Long id)
	{
		return sessionFactory.getCurrentSession().load(clazz, id);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Object loadByName(Class clazz, String name)
	{
		return sessionFactory.getCurrentSession().createQuery("from " + clazz.getName() + " where name like '" + name + "'").setCacheable(true).uniqueResult();
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List list(Class clazz)
	{
		return sessionFactory.getCurrentSession().createQuery("from " + clazz.getName()).list();
	}
	
	@Override
	public void save(Object entity)
	{
		sessionFactory.getCurrentSession().save(entity);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void removeById(Class clazz, Long id)
	{
		logger.warning("Delete entity[" + clazz + "] id[" + id + "]");
		Object entity = loadById(clazz, id);
		if (entity != null)
		{
			sessionFactory.getCurrentSession().delete(entity);
		}
	}
	
	@Override
	public void removeBy(Object entity)
	{
		logger.warning("Delete entity " + entity);
		sessionFactory.getCurrentSession().delete(entity);
	}
}