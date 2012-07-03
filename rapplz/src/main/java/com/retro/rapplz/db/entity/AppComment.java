package com.retro.rapplz.db.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="app_comment")
public class AppComment extends BaseMessage
{
	@ManyToOne
	@JoinColumn(name="app_id")
	private App app;
	
	public AppComment()
	{
		
	}

	public App getApp() {
		return app;
	}

	public void setApp(App app) {
		this.app = app;
	}
}