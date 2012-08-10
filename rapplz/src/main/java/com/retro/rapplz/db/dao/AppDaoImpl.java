package com.retro.rapplz.db.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.retro.rapplz.db.entity.App;

@Repository("appDao")
public class AppDaoImpl implements AppDao
{
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public App getApp(Long id)
	{
		return (App)sessionFactory.getCurrentSession().get(App.class, id);
	}

	public App getAppByName(String name)
	{
		return (App)sessionFactory.getCurrentSession().createQuery("select a from App a where a.name like '%" + name + "%'").uniqueResult();
	}
	
	@Override
	public App getAppByRawId(String rawId)
	{
		return (App)sessionFactory.getCurrentSession().createQuery("select a from App a where a.rawId like '" + rawId + "'").uniqueResult();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<App> getApps()
	{
		return sessionFactory.getCurrentSession().createQuery("from App app order by app.name").list();
	}

	@Override
	public void save(App app)
	{
		sessionFactory.getCurrentSession().save(app);
	}

	@Override
	public void remove(Long id)
	{
		sessionFactory.getCurrentSession().delete(id);
	}
}