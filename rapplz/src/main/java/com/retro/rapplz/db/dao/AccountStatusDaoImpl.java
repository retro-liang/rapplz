package com.retro.rapplz.db.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.retro.rapplz.db.entity.AccountRole;
import com.retro.rapplz.db.entity.AccountStatus;

@Repository("AccountStatusDao")
public class AccountStatusDaoImpl implements AccountStatusDao
{
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public AccountStatus getAccountStatusById(Long id)
	{
		return (AccountStatus)sessionFactory.getCurrentSession().load(AccountStatus.class, id);
	}
	
	@Override
	public AccountStatus getAccountStatusByName(String name)
	{
		return (AccountStatus)sessionFactory.getCurrentSession().createQuery("select as from AccountStatus as where as.name like '" + name + "'").setCacheable(true).uniqueResult();
	}
	
	@Override
	public List<AccountStatus> listAccountStatuss()
	{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AccountStatus.class);
		return (List<AccountStatus>)criteria.list();
	}
	
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
	public void removeAccountStatus(Long id)
	{
		AccountStatus AccountStatus = (AccountStatus) sessionFactory.getCurrentSession().load(AccountStatus.class, id);
		if (null != AccountStatus)
		{
			sessionFactory.getCurrentSession().delete(AccountStatus);
		}
	}
}