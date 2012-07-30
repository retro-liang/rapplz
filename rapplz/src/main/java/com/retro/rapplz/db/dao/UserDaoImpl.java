package com.retro.rapplz.db.dao;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.retro.rapplz.db.entity.AccountRole;
import com.retro.rapplz.db.entity.App;
import com.retro.rapplz.db.entity.User;
import com.retro.rapplz.service.exception.ApplicationServiceException;

@Repository("userDao")
@Transactional
public class UserDaoImpl implements UserDao
{
	private static final Logger logger = Logger.getLogger(UserDaoImpl.class.getName());
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void addUser(User user)
	{
		try
		{
			sessionFactory.getCurrentSession().save(user);
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}

	@Override
	public User findByEmail(String email)
	{
		logger.info("findByEmail email: " + email);
		User user = (User)sessionFactory.getCurrentSession().createQuery("select u from User u where u.email = '" + email + "'").uniqueResult();
		logger.info("findByEmail user: " + user);
		return user;
	}

	@Override
	public User getUserByID(Long id)
	{

		User user = (User)sessionFactory.getCurrentSession().createQuery("select u from User u where id = '" + id + "'").uniqueResult();
		return user;
	}
	
	@Override
	public void resetPassword(String email, String password)
	{
		String hql = "update User set password = :password where email = :email";
		org.hibernate.Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString("password", password);
		query.setString("email", email);
		query.executeUpdate();
	}

	@Override
	public String activateUser(Long id)
	{
		String hql = "update User u set u.accountStatus = (select s.id from AccountStatus s where s.name = 'ACTIVE' ) where u.id = :id";
		org.hibernate.Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setLong("id", id);
		int rowCount = query.executeUpdate();
		System.out.println("Rows affected: " + rowCount);
		return "";
	}

	@Override
	public String inactivateUser(Long id)
	{
		String hql = "update User u set u.accountStatus = (select s.id from AccountStatus s where s.name = 'INACTIVE' ) where u.id = :id";
		org.hibernate.Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setInteger("active", 2);
		query.setLong("id", id);
		int rowCount = query.executeUpdate();
		System.out.println("Rows affected: " + rowCount);
		return "";
	}

	@Override
	public void updateUser(User user)
	{
		try
		{
			sessionFactory.getCurrentSession().update(user);
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}

	@Override
	public List<User> listUser()
	{
		return (List<User>)sessionFactory.getCurrentSession().createQuery("from User").list();
	}

	@Override
	public void removeUser(Long id)
	{
		User user = (User) sessionFactory.getCurrentSession().load(
		User.class, id);
		if (null != user)
		{
			sessionFactory.getCurrentSession().delete(user);
		}
	}

	@Override
	public Set<AccountRole> getAccountRolesByEmail(String email)
	{
		User user = (User) sessionFactory.getCurrentSession().createQuery("select u from User u where u.email = '" + email + "'").uniqueResult();
		if (user!= null)
		{
			Set<AccountRole> roles = (Set<AccountRole>)user.getAccountRoles();
			if(roles != null && roles.size() > 0)
			{
				return roles;
			}
		}
		return null;
	}
	
	@Override
	public int getUserAppCount(Long id)
	{
		String sqlQuery = "select count(id) from user_app where user_id = ?";
		SQLQuery q = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery);
		q.setParameter(0, id);
		return ((BigInteger)q.uniqueResult()).intValue();
	}
	
	@Override
	public int getUserRecommendationCount(Long id)
	{
		String sqlQuery = "select count(id) from recommendation where from_user_id = ?";
		SQLQuery q = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery);
		q.setParameter(0, id);
		return ((BigInteger)q.uniqueResult()).intValue();
	}
	
	@Override
	public int getUserFollowerCount(Long id)
	{
		String sqlQuery = "select count(id) from follower_following where following_user_id = ?";
		SQLQuery q = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery);
		q.setParameter(0, id);
		return ((BigInteger)q.uniqueResult()).intValue();
	}
	
	@Override
	public int getUserFollowingCount(Long id)
	{
		String sqlQuery = "select count(id) from follower_following where follower_user_id = ?";
		SQLQuery q = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery);
		q.setParameter(0, id);
		return ((BigInteger)q.uniqueResult()).intValue();
	}
	
	public void have(Long userId, String rawId, String appName, String icon, String storeUrl) throws ApplicationServiceException
	{
		Session session = sessionFactory.getCurrentSession();
		App app = (App)session.createQuery("select a from App a where a.rawId = '" + rawId + "'").uniqueResult();
		if(app == null)
		{
			app = new App();
			app.setRawId(rawId);
			app.setName(appName);
			app.setIconUrl(icon);
			app.setAppStoreUrl(storeUrl);
			session.save(app);
		}
		User user = (User)sessionFactory.getCurrentSession().get(User.class, userId);
		user.getApps().add(app);
		session.save(user);
	}
	
	public void recommend(Long fromUserId, Long[] toUserIds, String rawId, String appName, String icon, String storeUrl) throws ApplicationServiceException
	{
		
	}
}