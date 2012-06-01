package com.retro.rapplz.server.datastore.entity;

import java.util.Date;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Unindexed;

@Entity
public class UserMessage
{
	@Id
	private Long id;
	
	private Key<User> authorKey;
	
	@Unindexed
	private String text;
	
	@Unindexed
	private Date createdDate;
	
	public UserMessage()
	{
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Key<User> getAuthorKey() {
		return authorKey;
	}

	public void setAuthorKey(Key<User> authorKey) {
		this.authorKey = authorKey;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
}