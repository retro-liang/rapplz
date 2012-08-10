package com.retro.rapplz.db.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.retro.rapplz.db.entity.Recommendation;

@Repository
public class RecommendationDaoImpl implements RecommendationDao
{
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Recommendation getRecommendation(Long id)
	{
		return (Recommendation)sessionFactory.getCurrentSession().get(Recommendation.class, id);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Recommendation> getCategories()
	{
		return sessionFactory.getCurrentSession().createQuery("from Recommendation").list();		
	}

	@Override
	public void save(Recommendation recommendation)
	{
		sessionFactory.getCurrentSession().save(recommendation);
	}

	@Override
	public void remove(Long id)
	{
		sessionFactory.getCurrentSession().delete(id);
	}
}