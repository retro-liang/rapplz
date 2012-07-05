package com.retro.rapplz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.retro.rapplz.db.dao.UserDao;

@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService
{
	@Autowired
	private UserDao userDao;
	
	@Override
	public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException
	{
		return null;
	}
}