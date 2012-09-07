package com.retro.rapplz.db.dao;

import java.math.BigInteger;
import java.util.Set;
import java.util.logging.Logger;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.retro.rapplz.db.entity.AccountRole;
import com.retro.rapplz.db.entity.User;

@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl implements UserDao
{
	private static final Logger logger = Logger.getLogger(UserDaoImpl.class.getName());
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public User getUserByEmail(String email)
	{
		logger.info("findByEmail email: " + email);
		return (User)sessionFactory.getCurrentSession().createQuery("select u from User u where u.email = '" + email + "'").setCacheable(true).uniqueResult();
	}
	
	@Override
	public User getRapplzUserByEmail(String email)
	{
		logger.info("getRapplzUserByEmail email: " + email);
		return (User)sessionFactory.getCurrentSession().createQuery("select u from User u where u.email = '" + email + "' and u.accountType = 1").setCacheable(true).uniqueResult();
	}
	
	@Override
	public User getGoogleUserByEmail(String email)
	{
		logger.info("getGoogleUserByEmail email: " + email);
		return (User)sessionFactory.getCurrentSession().createQuery("select u from User u where u.email = '" + email + "' and u.accountType = 2").setCacheable(true).uniqueResult();
	}
	
	@Override
	public User getFacebookUserByEmail(String email)
	{
		logger.info("getFacebookUserByEmail email: " + email);
		return (User)sessionFactory.getCurrentSession().createQuery("select u from User u where u.email = '" + email + "' and u.accountType = 3").setCacheable(true).uniqueResult();
	}
	
	@Override
	public User getTwitterUserByEmail(String email)
	{
		logger.info("getTwitterUserByEmail email: " + email);
		return (User)sessionFactory.getCurrentSession().createQuery("select u from User u where u.email = '" + email + "' and u.accountType = 4").setCacheable(true).uniqueResult();
	}
	
	
	@Override
	public User getUserByEmailAccountType(String email, String accountType)
	{
		logger.info("getUserByEmailAccountType email: " + email);
		User user = (User)sessionFactory.getCurrentSession().createQuery("select u from User u left join AccountType at on u.accountType = at.id where u.email = '" + email + "' and at.name='" + accountType + "'").setCacheable(true).uniqueResult();
		return user;
	}

	@Override
	public User getUserByFederalId(String id)
	{
		return (User)sessionFactory.getCurrentSession().createQuery("select u from User u where u.federalId = '" + id + "'").setCacheable(true).uniqueResult();
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
	public Set<AccountRole> getAccountRolesByEmail(String email)
	{
		User user = (User) sessionFactory.getCurrentSession().createQuery("select u from User u where u.email = '" + email + "'").setCacheable(true).uniqueResult();
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
	
	@Override
	public boolean alreadyHave(Long userId, Long appId)
	{
		String sqlQuery = "select count(id) from user_app where user_id = ? and app_id = ?";
		SQLQuery q = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery);
		q.setCacheable(true);
		q.setParameter(0, userId);
		q.setParameter(1, appId);
		return ((BigInteger)q.uniqueResult()).intValue() > 0;
	}
	
	@Override
	public boolean alreadyRecommend(Long userId, Long appId)
	{
		String sqlQuery = "select count(id) from recommendation where from_user_id = ? and app_id = ?";
		SQLQuery q = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery);
		q.setCacheable(true);
		q.setParameter(0, userId);
		q.setParameter(1, appId);
		return ((BigInteger)q.uniqueResult()).intValue() > 0;
	}
}