package com.retro.rapplz.server.datastore.entity;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Entity;

@Cached
@Entity
public class Profile
{
	@Id
	private Long id;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	public Profile()
	{
		
	}
}
