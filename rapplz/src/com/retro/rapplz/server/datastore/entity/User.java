package com.retro.rapplz.server.datastore.entity;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Entity;

@Cached
@Entity
public class User
{
	@Id
	private String id;
	
	private Key<Account> account;
	
	private Key<Profile> profile;
	
	public User()
	{
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Key<Account> getAccount() {
		return account;
	}

	public void setAccount(Key<Account> account) {
		this.account = account;
	}

	public Key<Profile> getProfile() {
		return profile;
	}

	public void setProfile(Key<Profile> profile) {
		this.profile = profile;
	}	
}