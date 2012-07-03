package com.retro.rapplz.db.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@SuppressWarnings("serial")
@Entity
@Table(name="device")
public class Device extends BaseEntity
{
	private String name;
	
	@ManyToMany
	(
        mappedBy = "devices",
        targetEntity = App.class
    )
	@Cascade(CascadeType.SAVE_UPDATE)
	private Set<App> apps;
	
	public Device()
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