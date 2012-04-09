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
	private Key<User> userKey;
	
	private Set<Key<User>> followerKeys = new HashSet<Key<User>>();
	
	private Set<Key<User>> followedKeys = new HashSet<Key<User>>();
	
	public UserIndex()
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

	public Set<Key<User>> getFollowerKeys() {
		return followerKeys;
	}

	public void setFollowerKeys(Set<Key<User>> followerKeys) {
		this.followerKeys = followerKeys;
	}

	public Set<Key<User>> getFollowedKeys() {
		return followedKeys;
	}

	public void setFollowedKeys(Set<Key<User>> followedKeys) {
		this.followedKeys = followedKeys;
	}
}