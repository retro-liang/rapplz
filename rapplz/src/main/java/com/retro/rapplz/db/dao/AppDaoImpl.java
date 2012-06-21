package com.retro.rapplz.db.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.retro.rapplz.db.entity.App;

@Repository
@Transactional
public class AppDaoImpl implements AppDao
{
	private SessionFactory sessionFactory;
	
	@Autowired
	public AppDaoImpl(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public App loadApp(Long id)
	{
		return (App)sessionFactory.getCurrentSession().get(App.class, id);
	}

	@Override
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<App> getApps()
	{
		return sessionFactory.getCurrentSession().createQuery("from App app order by app.name").list();
		/*Session session = null;
		try
		{
			session = sessionFactory.openSession();
			List<App> apps = session.createQuery("from App app order by app.name").list();
			System.out.println("apps: " + apps);
			return apps;
		}
		finally
		{
			//session.clear();
			//sessionFactory.close();
			session.close();
		}*/
	}

	@Override
	public App saveApp(App app)
	{
		// Note: Hibernate3's merge operation does not reassociate the object
        // with the current Hibernate Session. Instead, it will always copy the
        // state over to a registered representation of the entity. In case of a
        // new entity, it will register a copy as well, but will not update the
        // id of the passed-in object. To still update the ids of the original
        // objects too, we need to register Spring's
        // IdTransferringMergeEventListener on our SessionFactory.
		
		return (App)sessionFactory.getCurrentSession().merge(app);
	}

	@Override
	public void removeApp(Long id)
	{
		App app = loadApp(id);
		sessionFactory.getCurrentSession().delete(app);
	
	}
}