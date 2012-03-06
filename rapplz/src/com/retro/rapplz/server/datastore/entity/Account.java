package com.retro.rapplz.server.datastore.entity;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Entity;

@Cached
@Entity
public class Account
{
	@Id
	private Long id;
	
	private String username;
	
	private String password;
	
	private Role role = new Role();
	
	private Status status = new Status();
	
	public Account()
	{
		
	}
}
