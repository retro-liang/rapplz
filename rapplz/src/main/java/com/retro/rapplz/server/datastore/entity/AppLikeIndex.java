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
public class AppLikeIndex
{
	@Id
	private Long id;
	
	@Parent
	private Key<App> appKey;
	
	private Set<Key<AppLike>> appLikeKeys = new HashSet<Key<AppLike>>();
	
	public AppLikeIndex()
	{
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Key<App> getAppKey() {
		return appKey;
	}

	public void setAppKey(Key<App> appKey) {
		this.appKey = appKey;
	}

	public Set<Key<AppLike>> getAppLikeKeys() {
		return appLikeKeys;
	}

	public void setAppLikeKeys(Set<Key<AppLike>> appLikeKeys) {
		this.appLikeKeys = appLikeKeys;
	}
}