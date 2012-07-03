package com.retro.rapplz.db.entity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="os")
public class OS extends BaseEntity
{
	private String name;
	
	@OneToMany(mappedBy="os")
	private App app;
	
	public OS()
	{
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public App getApp() {
		return app;
	}

	public void setApp(App app) {
		this.app = app;
	}
}