package com.retro.rapplz.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="tag")
public class Tag extends BaseEntity
{
	@Column(name="tag_name_id")
	private TagName tagName;
	
	@ManyToOne
	@JoinColumn(name="author_user_id")
	private User author;
	
	@ManyToOne
	@JoinColumn(name="app_id")
	private App app;
	
	public Tag()
	{
		
	}

	public TagName getTagName() {
		return tagName;
	}

	public void setTagName(TagName tagName) {
		this.tagName = tagName;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public App getApp() {
		return app;
	}

	public void setApp(App app) {
		this.app = app;
	}
}