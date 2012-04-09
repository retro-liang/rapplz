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
public class AppReviewIndex
{
	@Id
	private Long id;
	
	@Parent
	private Key<App> appKey;
	
	private Set<Key<UserMessage>> reviewKeys = new HashSet<Key<UserMessage>>();
	
	public AppReviewIndex()
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

	public Set<Key<UserMessage>> getReviewKeys() {
		return reviewKeys;
	}

	public void setReviewKeys(Set<Key<UserMessage>> reviewKeys) {
		this.reviewKeys = reviewKeys;
	}
}