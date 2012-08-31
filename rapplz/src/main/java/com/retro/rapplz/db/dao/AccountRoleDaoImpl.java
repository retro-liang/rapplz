package com.retro.rapplz.db.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.retro.rapplz.db.entity.AccountRole;
import com.retro.rapplz.db.entity.App;

@Repository("accountRoleDao")
public class AccountRoleDaoImpl implements AccountRoleDao
{
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public AccountRole getAccountRoleById(Long id)
	{
		return (AccountRole)sessionFactory.getCurrentSession().load(AccountRole.class, id);
	}
	
	@Override
	public AccountRole getAccountRoleByName(String name)
	{
		return (AccountRole)sessionFactory.getCurrentSession().createQuery("select ar from AccountRole ar where ar.name like '" + name + "'").setCacheable(true).uniqueResult();
	}
	
	@Override
	public List<AccountRole> listAccountRoles()
	{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AccountRole.class);
		return (List<AccountRole>)criteria.list();
	}
	
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
	public void removeAccountRole(Long id)
	{
		AccountRole accountRole = (AccountRole) sessionFactory.getCurrentSession().load(AccountRole.class, id);
		if (null != accountRole)
		{
			sessionFactory.getCurrentSession().delete(accountRole);
		}
	}
}