package com.retro.rapplz.db.entity;

import javax.persistence.Entity;

@SuppressWarnings("serial")
@Entity
public class AppComment extends BaseMessage
{
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