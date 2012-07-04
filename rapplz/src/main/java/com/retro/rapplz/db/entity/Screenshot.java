package com.retro.rapplz.db.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="screenshot")
public class Screenshot extends BaseEntity
{
	private String type;
	
	private String url;
	
	@ManyToOne
	@JoinColumn(name="app_id")
	private App app;
	
	public Screenshot()
	{
		
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public App getApp() {
		return app;
	}

	public void setApp(App app) {
		this.app = app;
	}
}