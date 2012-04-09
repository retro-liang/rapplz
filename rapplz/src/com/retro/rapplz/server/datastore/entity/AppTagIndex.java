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
public class AppTagIndex
{
	@Id
	private Long id;
	
	@Parent
	private Key<AppTag> appTagKey;
	
	private Set<Key<App>> appKeys = new HashSet<Key<App>>();
	
	private Set<Key<User>> userKeys = new HashSet<Key<User>>();
	
	public AppTagIndex()
	{
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Key<AppTag> getAppTagKey() {
		return appTagKey;
	}

	public void setAppTagKey(Key<AppTag> appTagKey) {
		this.appTagKey = appTagKey;
	}

	public Set<Key<App>> getAppKeys() {
		return appKeys;
	}

	public void setApps(Set<Key<App>> appKeys) {
		this.appKeys = appKeys;
	}

	public Set<Key<User>> getUserKeys() {
		return userKeys;
	}

	public void setUsers(Set<Key<User>> userKeys) {
		this.userKeys = userKeys;
	}
}