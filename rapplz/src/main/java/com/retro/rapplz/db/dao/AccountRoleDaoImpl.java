package com.retro.rapplz.db.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.retro.rapplz.db.entity.AccountRole;

@Repository("accountRoleDao")
@Transactional
public class AccountRoleDaoImpl implements AccountRoleDao
{
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void addAccountRole(AccountRole accountRole)
	{
		try
		{
			sessionFactory.getCurrentSession().save(accountRole);
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}

	@Override
	public List<AccountRole> listAccountRoles()
	{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AccountRole.class);
		return (List<AccountRole>)criteria.list();
	}

	@Override
	public AccountRole getAccountRoleById(Long id)
	{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AccountRole.class);
		criteria.add(Restrictions.eq("id", id));
		return (AccountRole) criteria.uniqueResult();
	}
	
	@Override
	public AccountRole getAccountRoleByName(String name)
	{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AccountRole.class);
		criteria.add(Restrictions.eq("name", name));
		return (AccountRole) criteria.uniqueResult();
	}

	@Override
	public void removeAccountRole(Long id)
	{
		AccountRole accountRole = (AccountRole) sessionFactory.getCurrentSession().load(AccountRole.class, id);
		if (null != accountRole)
		{
			sessionFactory.getCurrentSession().delete(accountRole);
		}
	}
}