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
	
	private Key<Account> accountKey;
	
	private Key<Profile> profileKey;
	
	public User()
	{
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Key<Account> getAccountKey() {
		return accountKey;
	}

	public void setAccountKey(Key<Account> accountKey) {
		this.accountKey = accountKey;
	}

	public Key<Profile> getProfileKey() {
		return profileKey;
	}

	public void setProfileKey(Key<Profile> profileKey) {
		this.profileKey = profileKey;
	}	
}