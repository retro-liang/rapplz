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
public class UserMessageBox
{
	@Id
	private Long id;
	
	@Parent
	private Key<User> authorKey;
	
	private Set<Key<UserMessage>> userMessageKeys = new HashSet<Key<UserMessage>>();
	
	private boolean read;
	
	public UserMessageBox()
	{
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Key<User> getAuthorKey() {
		return authorKey;
	}

	public void setAuthorKey(Key<User> authorKey) {
		this.authorKey = authorKey;
	}

	public Set<Key<UserMessage>> getUserMessageKeys() {
		return userMessageKeys;
	}

	public void setUserMessageKeys(Set<Key<UserMessage>> userMessageKeys) {
		this.userMessageKeys = userMessageKeys;
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}
}