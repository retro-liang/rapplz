package com.retro.rapplz.db.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="app_category")
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