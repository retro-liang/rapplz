package com.retro.rapplz.db.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="recommendation")
public class Recommendation extends BaseEntity
{
	@ManyToOne
	@JoinColumn(name="app_id")
	private App app;
	
	@ManyToOne
	@JoinColumn(name="from_user_id")
	private User fromUser;
	
	@ManyToOne
	@JoinColumn(name="to_user_id")
	private User toUser;
	
	private String message;
	
	public Recommendation()
	{
		
	}

	public App getApp() {
		return app;
	}

	public void setApp(App app) {
		this.app = app;
	}

	public User getFromUser() {
		return fromUser;
	}

	public void setFromUser(User fromUser) {
		this.fromUser = fromUser;
	}

	public User getToUser() {
		return toUser;
	}

	public void setToUser(User toUser) {
		this.toUser = toUser;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}