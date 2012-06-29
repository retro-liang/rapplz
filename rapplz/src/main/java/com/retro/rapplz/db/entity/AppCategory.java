package com.retro.rapplz.db.entity;

import javax.persistence.Entity;

@SuppressWarnings("serial")
@Entity
public class AppCategory extends BaseEntity
{
	private String name;
	
	public AppCategory()
	{
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}