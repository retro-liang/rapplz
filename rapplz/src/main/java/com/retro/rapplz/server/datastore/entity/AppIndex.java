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
public class AppIndex
{
	@Id
	private Long id;
	
	@Parent
	private Key<User> userKey;
	
	private Set<Key<App>> appKeys = new HashSet<Key<App>>();
	
	public AppIndex()
	{
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Key<User> getUserKey() {
		return userKey;
	}

	public void setUserKey(Key<User> userKey) {
		this.userKey = userKey;
	}

	public Set<Key<App>> getAppKeys() {
		return appKeys;
	}

	public void setAppKeys(Set<Key<App>> appKeys) {
		this.appKeys = appKeys;
	}
}
