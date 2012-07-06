package com.retro.rapplz.service;

import org.springframework.beans.factory.annotation.Autowired;
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
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserAssembler userAssembler;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
	{
		User userEntity = userDao.findByEmail(email);
		if (userEntity == null)
		{
			throw new UsernameNotFoundException("user not found");
		}
		return userAssembler.buildUserFromUserEntity(userEntity);
	}
}