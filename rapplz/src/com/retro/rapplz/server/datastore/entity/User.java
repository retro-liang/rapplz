package com.retro.rapplz.server.datastore.entity;



import javax.persistence.Id;

import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Entity;

@Cached
@Entity
public class User
{
	@Id
	private Long id;
	
	private Account account = new Account();
	
	private Profile profile = new Profile();
	
	public User()
	{
		
	}	
}
