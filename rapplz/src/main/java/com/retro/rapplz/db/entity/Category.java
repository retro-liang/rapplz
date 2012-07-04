package com.retro.rapplz.db.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@SuppressWarnings("serial")
@Entity
@Table(name="category")
public class Category extends BaseEntity
{
	private String name;
	
	@ManyToMany
	(
        mappedBy = "categories",
        targetEntity = App.class
    )
	@Cascade(CascadeType.SAVE_UPDATE)
	private Set<App> apps = new HashSet<App>();
	
	public Category()
	{
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<App> getApps() {
		return apps;
	}

	public void setApps(Set<App> apps) {
		this.apps = apps;
	}
}