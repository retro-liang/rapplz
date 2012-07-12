package com.retro.rapplz.db.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.retro.rapplz.db.entity.AccountStatus;

@Repository("AccountStatusDao")
@Transactional
public class AccountStatusDaoImpl implements AccountStatusDao
{
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void addAccountStatus(AccountStatus AccountStatus)
	{
		try
		{
			sessionFactory.getCurrentSession().save(AccountStatus);
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}

	@Override
	public List<AccountStatus> listAccountStatuss()
	{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AccountStatus.class);
		return (List<AccountStatus>)criteria.list();
	}

	@Override
	public AccountStatus getAccountStatusById(Long id)
	{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AccountStatus.class);
		criteria.add(Restrictions.eq("id", id));
		return (AccountStatus) criteria.uniqueResult();
	}
	
	@Override
	public AccountStatus getAccountStatusByName(String name)
	{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AccountStatus.class);
		criteria.add(Restrictions.eq("name", name));
		return (AccountStatus) criteria.uniqueResult();
	}

	@Override
	public void removeAccountStatus(Long id)
	{
		AccountStatus AccountStatus = (AccountStatus) sessionFactory.getCurrentSession().load(AccountStatus.class, id);
		if (null != AccountStatus)
		{
			sessionFactory.getCurrentSession().delete(AccountStatus);
		}
	}
}