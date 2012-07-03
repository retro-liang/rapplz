package com.retro.rapplz.db.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@SuppressWarnings("serial")
@Entity
@Table(name="screenshot")
public class Screenshot extends BaseEntity
{
	private String type;
	
	private String url;
	
	@ManyToMany
	(
        mappedBy = "screenshots",
        targetEntity = App.class
    )
	@Cascade(CascadeType.SAVE_UPDATE)
	private Set<App> apps;
	
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

	public Set<App> getApps() {
		return apps;
	}

	public void setApps(Set<App> apps) {
		this.apps = apps;
	}
}