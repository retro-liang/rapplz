package com.retro.rapplz.db.dao;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.retro.rapplz.db.entity.App;

@Repository("appDao")
public class AppDaoImpl extends BaseDaoImpl implements AppDao
{
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public App loadAppByRawId(String rawId)
	{
		return (App)sessionFactory.getCurrentSession().createQuery("from App where rawId like '" + rawId + "'").setCacheable(true).uniqueResult();
	}
	
	@Override
	public List<App> getAppsByCategory(Long categoryId)
	{
		String hql = "select distinct a from App a join a.categories ac where ac.id in (:categoryId)";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("categoryId", categoryId);
		return query.list();
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