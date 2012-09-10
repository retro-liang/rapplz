package com.retro.rapplz.db.dao;

import org.springframework.stereotype.Repository;

@Repository("newsletterDao")
public class NewsletterDaoImpl extends BaseDaoImpl implements NewsletterDao
{
	@Override
	public boolean isEmailExist(String email)
	{
		return (sessionFactory.getCurrentSession().createQuery("from Newsletter where email like '" + email + "'").setCacheable(true).uniqueResult() == null);
	}

	@Override
	public void deleteByEmail(String email)
	{
		
	}
}