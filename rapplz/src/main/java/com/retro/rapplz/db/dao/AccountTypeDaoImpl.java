package com.retro.rapplz.db.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.retro.rapplz.db.entity.AccountType;

@Repository("AccountTypeDao")
public class AccountTypeDaoImpl implements AccountTypeDao
{
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void addAccountType(AccountType AccountType)
	{
		try
		{
			sessionFactory.getCurrentSession().save(AccountType);
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}

	@Override
	public List<AccountType> listAccountTypes()
	{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AccountType.class);
		return (List<AccountType>)criteria.list();
	}

	@Override
	public AccountType getAccountTypeById(Long id)
	{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AccountType.class);
		criteria.add(Restrictions.eq("id", id));
		return (AccountType) criteria.uniqueResult();
	}
	
	@Override
	public AccountType getAccountTypeByName(String name)
	{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AccountType.class);
		criteria.add(Restrictions.eq("name", name));
		return (AccountType) criteria.uniqueResult();
	}

	@Override
	public void removeAccountType(Long id)
	{
		AccountType AccountType = (AccountType) sessionFactory.getCurrentSession().load(AccountType.class, id);
		if (null != AccountType)
		{
			sessionFactory.getCurrentSession().delete(AccountType);
		}
	}
}