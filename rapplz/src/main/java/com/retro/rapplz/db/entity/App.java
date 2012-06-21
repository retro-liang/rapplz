package com.retro.rapplz.db.entity;

import javax.persistence.Entity;

@SuppressWarnings("serial")
@Entity
public class App extends BaseEntity
{
	private String name;
	
	public App()
	{
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}