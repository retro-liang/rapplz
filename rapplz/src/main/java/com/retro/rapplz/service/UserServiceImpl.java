package com.retro.rapplz.service;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.retro.rapplz.db.dao.AccountRoleDao;
import com.retro.rapplz.db.dao.AccountStatusDao;
import com.retro.rapplz.db.dao.AccountTypeDao;
import com.retro.rapplz.db.dao.UserDao;
import com.retro.rapplz.db.entity.AccountRole;
import com.retro.rapplz.db.entity.AccountStatus;
import com.retro.rapplz.db.entity.AccountType;
import com.retro.rapplz.db.entity.User;
import com.retro.rapplz.service.exception.ApplicationServiceException;
import com.retro.rapplz.util.UserAssembler;

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
	private UserAssembler userAssembler;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException, DataAccessException
	{
		logger.info("loadUserByUsername: " + email);
		User user = userDao.findByEmail(email);
		if (user == null)
		{
			throw new UsernameNotFoundException("user not found");
		}
		return userAssembler.buildUserFromUserEntity(user);
	}
	
	@Override
	public User createUser(String accountRoleName, String accountTypeName, String accountStatusName, String email, String password, String firstName, String lastName) throws ApplicationServiceException
	{
		logger.info("createUser: " + email);
		AccountRole accountRole = accountRoleDao.getAccountRoleByName(accountRoleName);
		AccountType accountType = accountTypeDao.getAccountTypeByName(accountTypeName);
		AccountStatus accountStatus = accountStatusDao.getAccountStatusByName(accountStatusName);
		User user = new User();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setPassword(password);
		user.getAccountRoles().add(accountRole);
		user.setAccountType(accountType);
		user.setAccountStatus(accountStatus);
		userDao.addUser(user);
		return user;
	}
}