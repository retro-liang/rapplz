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
import com.retro.rapplz.db.entity.User;

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
}