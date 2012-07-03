package com.retro.rapplz.db.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="tag_name")
public class TagName extends BaseEntity
{
	private String name;
	
	public TagName()
	{
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}