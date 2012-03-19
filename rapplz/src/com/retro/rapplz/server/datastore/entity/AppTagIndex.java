package com.retro.rapplz.server.datastore.entity;

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
	private Key<AppTag> appTag;
	
	private Set<Key<App>> apps;
	
	private Set<Key<User>> users;
	
	public AppTagIndex()
	{
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Key<AppTag> getAppTag() {
		return appTag;
	}

	public void setAppTag(Key<AppTag> appTag) {
		this.appTag = appTag;
	}

	public Set<Key<App>> getApps() {
		return apps;
	}

	public void setApps(Set<Key<App>> apps) {
		this.apps = apps;
	}

	public Set<Key<User>> getUsers() {
		return users;
	}

	public void setUsers(Set<Key<User>> users) {
		this.users = users;
	}
}