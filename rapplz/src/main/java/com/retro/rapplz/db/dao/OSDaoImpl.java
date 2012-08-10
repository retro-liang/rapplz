package com.retro.rapplz.db.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.retro.rapplz.db.entity.OS;

@Repository
public class OSDaoImpl implements OSDao
{
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public OS getOS(Long id)
	{
		return (OS)sessionFactory.getCurrentSession().get(OS.class, id);
	}
	
	@Override
	public OS getOSByName(String name)
	{
		return (OS)sessionFactory.getCurrentSession().createQuery("select o from OS o where o.name like '" + name + "'").uniqueResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<OS> getOSs()
	{
		return sessionFactory.getCurrentSession().createQuery("from OS").list();		
	}

	@Override
	public void save(OS os)
	{
		sessionFactory.getCurrentSession().save(os);
	}

	@Override
	public void remove(Long id)
	{
		sessionFactory.getCurrentSession().delete(id);
	}
}