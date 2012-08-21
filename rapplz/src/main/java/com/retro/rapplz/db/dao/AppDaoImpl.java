package com.retro.rapplz.db.dao;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.SQLQuery;
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
	
	@Override
	public int getAppHaveCount(Long id)
	{
		String sqlQuery = "select count(id) from user_app where app_id = ?";
		SQLQuery q = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery);
		q.setParameter(0, id);
		return ((BigInteger)q.uniqueResult()).intValue();
	}
	
	public int getAppRecommendationCount(Long id)
	{
		String sqlQuery = "select count(id) from recommendation where app_id = ?";
		SQLQuery q = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery);
		q.setParameter(0, id);
		return ((BigInteger)q.uniqueResult()).intValue();
	}
}