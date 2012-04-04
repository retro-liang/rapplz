package com.retro.rapplz.server.datastore.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Parent;

@Cached
@Entity
public class UserIndex
{
	@Id
	private Long id;
	
	@Parent
	private Key<User> user;
	
	private Set<Key<User>> followers = new HashSet<Key<User>>();
	
	public UserIndex()
	{
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Key<User> getUser() {
		return user;
	}

	public void setUser(Key<User> user) {
		this.user = user;
	}

	public Set<Key<User>> getFollowers() {
		return followers;
	}

	public void setFollowers(Set<Key<User>> followers) {
		this.followers = followers;
	}	
}