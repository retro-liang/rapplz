package com.retro.rapplz.db.entity;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@MappedSuperclass
public class BaseMessage extends BaseEntity
{
	@ManyToOne
	@JoinColumn(name="author_user_id")
	private User author;
	
	private String content;
	
	@Transient
	protected Object[] jdoDetachedState;
	
	public BaseMessage()
	{
		
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}