package com.retro.rapplz.service;

import java.util.logging.Logger;

import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.retro.rapplz.config.RapplzConfig;
import com.retro.rapplz.db.dao.AccountRoleDao;
import com.retro.rapplz.db.dao.AccountStatusDao;
import com.retro.rapplz.db.dao.AccountTypeDao;
import com.retro.rapplz.db.dao.UserDao;
import com.retro.rapplz.db.entity.AccountRole;
import com.retro.rapplz.db.entity.AccountStatus;
import com.retro.rapplz.db.entity.AccountType;
import com.retro.rapplz.db.entity.User;
import com.retro.rapplz.security.EncryptAES;
import com.retro.rapplz.service.exception.ApplicationServiceException;

@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService
{
	private static final Logger logger = Logger.getLogger(UserServiceImpl.class.getName());
	
	@Autowired
	private AccountRoleDao accountRoleDao;
	
	@Autowired
	private AccountTypeDao accountTypeDao;
	
	@Autowired
	private AccountStatusDao accountStatusDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private SaltSource saltSource;
	
	@Override
	@Transactional(readOnly = true)
	public User loadUserByUsername(String email) throws UsernameNotFoundException, DataAccessException
	{
		logger.info("loadUserByUsername: " + email);
		User user = userDao.findByEmail(email);
		if (user == null)
		{
			throw new UsernameNotFoundException("user not found");
		}
		return user;
	}
	
	@Override
	@Transactional
	public User createUser(String accountRoleName, String accountTypeName, String accountStatusName, String email, String password, String firstName, String lastName) throws ApplicationServiceException
	{
		logger.info("createUser: " + email);
		User user = new User();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setPassword(password);
		user.setPassword(passwordEncoder.encodePassword(password, saltSource.getSalt(user)));
		AccountRole accountRole = accountRoleDao.getAccountRoleByName(accountRoleName);
		AccountType accountType = accountTypeDao.getAccountTypeByName(accountTypeName);
		AccountStatus accountStatus = accountStatusDao.getAccountStatusByName(accountStatusName);
		user.getAccountRoles().add(accountRole);
		user.setAccountType(accountType);
		user.setAccountStatus(accountStatus);
		userDao.addUser(user);
		return user;
	}
	
	@Override
	@Transactional
	public void resetPassword(String email, String password) throws ApplicationServiceException
	{
		logger.info("reset password: " + email);
		User user = loadUserByUsername(email);
	    String encodedPassword = passwordEncoder.encodePassword(password, saltSource.getSalt(user));
		userDao.resetPassword(email, encodedPassword);
	}
	
	@Override
	@Transactional
	public void activateUser(Long id) throws ApplicationServiceException
	{
		logger.info("activate user: " + id);
		userDao.activateUser(id);
	}
	
	@Override
	@Transactional
	public void inactivateUser(Long id) throws ApplicationServiceException
	{
		logger.info("inactivate user: " + id);
		userDao.inactivateUser(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public int getUserAppCount(Long id) throws ApplicationServiceException
	{
		return userDao.getUserAppCount(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public int getUserRecommendationCount(Long id) throws ApplicationServiceException
	{
		return userDao.getUserRecommendationCount(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public int getUserFollowerCount(Long id) throws ApplicationServiceException
	{
		return userDao.getUserFollowerCount(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public int getUserFollowingCount(Long id) throws ApplicationServiceException
	{
		return userDao.getUserFollowingCount(id);
	}
	
	@Override
	@Transactional
	public void have(Long userId, String rawId, String appName, String icon, String storeUrl) throws ApplicationServiceException
	{
		userDao.have(userId, rawId, appName, icon, storeUrl);
	}
	
	@Override
	@Transactional
	public void recommend(Long fromUserId, Long[] toUserIds, String rawId, String appName, String icon, String storeUrl) throws ApplicationServiceException
	{
		userDao.recommend(fromUserId, toUserIds, rawId, appName, icon, storeUrl);
	}
}