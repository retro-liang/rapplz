package com.retro.rapplz.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.retro.rapplz.db.entity.AccountRole;

@Service("userAssembler")
public class UserAssembler
{
	@Transactional(readOnly = true)

	public User buildUserFromUserEntity(com.retro.rapplz.db.entity.User user)
	{
		String username = user.getEmail();
		String password = user.getPassword();
		boolean enabled = user.getAccountStatus().getName().trim().equalsIgnoreCase("ACTIVE");
		boolean accountNonExpired = user.getAccountStatus().getName().trim().equalsIgnoreCase("ACTIVE");
		boolean credentialsNonExpired = user.getAccountStatus().getName().trim().equalsIgnoreCase("ACTIVE");
		boolean accountNonLocked = user.getAccountStatus().getName().trim().equalsIgnoreCase("ACTIVE");
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		for (AccountRole accountRole : user.getAccountRoles())
		{
			authorities.add(new SimpleGrantedAuthority(accountRole.getName()));
		}
		User springUser = new User(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		return springUser;
	}
}