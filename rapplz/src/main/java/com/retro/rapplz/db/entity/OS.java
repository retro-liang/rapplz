package com.retro.rapplz.db.entity;

import java.util.HashSet;
import java.util.Set;

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
	private Set<App> app = new HashSet<App>();
	
	public OS()
	{
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<App> getApp() {
		return app;
	}

	public void setApp(Set<App> app) {
		this.app = app;
	}
}