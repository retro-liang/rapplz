package com.retro.rapplz.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.retro.rapplz.db.entity.AccountRole;

@Service("userAssembler")
public class UserAssembler
{
	private static final Logger logger = Logger.getLogger(UserAssembler.class.getName());
	
	@Transactional(readOnly = true)
	public User buildUserFromUserEntity(com.retro.rapplz.db.entity.User user)
	{
		String username = user.getEmail();
		String password = user.getPassword();
		boolean enabled = true;	//not used
		boolean accountNonExpired = true;	//not used
		boolean credentialsNonExpired = true;	//not used
		boolean accountNonLocked = true;	//not used
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		for (AccountRole accountRole : user.getAccountRoles())
		{
			authorities.add(new SimpleGrantedAuthority(accountRole.getName()));
		}
		User springUser = new User(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		logger.info("springUser: " + springUser);
		return springUser;
	}
}