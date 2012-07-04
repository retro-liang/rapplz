package com.retro.rapplz.db.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.retro.rapplz.db.entity.OS;

@Repository
@Transactional
public class OSDaoImpl implements OSDao
{
	private SessionFactory sessionFactory;
	
	@Autowired
	public OSDaoImpl(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public OS loadOS(Long id)
	{
		return (OS)sessionFactory.getCurrentSession().get(OS.class, id);
	}

	@Override
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<OS> getOSs()
	{
		return sessionFactory.getCurrentSession().createQuery("from OS").list();		
	}

	@Override
	public OS saveOS(OS os)
	{
		// Note: Hibernate3's merge operation does not reassociate the object
        // with the current Hibernate Session. Instead, it will always copy the
        // state over to a registered representation of the entity. In case of a
        // new entity, it will register a copy as well, but will not update the
        // id of the passed-in object. To still update the ids of the original
        // objects too, we need to register Spring's
        // IdTransferringMergeEventListener on our SessionFactory.
		
		return (OS)sessionFactory.getCurrentSession().merge(os);
	}

	@Override
	public void removeOS(Long id)
	{
		OS os = loadOS(id);
		sessionFactory.getCurrentSession().delete(os);
	
	}
}