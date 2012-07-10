package com.retro.rapplz.service;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.retro.rapplz.db.dao.UserDao;
import com.retro.rapplz.db.entity.AccountRole;
import com.retro.rapplz.db.entity.AccountStatus;
import com.retro.rapplz.db.entity.AccountType;
import com.retro.rapplz.db.entity.User;
import com.retro.rapplz.service.exception.ApplicationServiceException;

@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService
{
	private static final Logger logger = Logger.getLogger(UserServiceImpl.class.getName());
	
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
	public User createUser(String accountType, String email, String password, String firstName, String lastName) throws ApplicationServiceException
	{
		logger.info("createUser: " + email);
		/*AccountType accountType = new AccountType();
		accountType.setId(1l);
		accountType.setName("RAPPLZ");
		user.setAccountType(accountType);*/
		User user = new User();
		user.setEmail(email);
		user.setPassword(password);
		AccountStatus accountStatus = new AccountStatus();
		accountStatus.setId(1l);
		accountStatus.setName(AccountStatus.DEFAULT);
		user.setAccountStatus(accountStatus);
		AccountRole accountRole = new AccountRole();
		accountRole.setId(1l);
		accountRole.setName("ROLE_USER");
		user.getAccountRoles().add(accountRole);		
		userDao.addUser(user);
		return null;
	}
}