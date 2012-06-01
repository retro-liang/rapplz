package com.retro.rapplz.server.datastore.entity;

import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Entity;

@Cached
@Entity
@XmlRootElement
public class AppTag
{
	@Id
	private Long id;
	
	private String name;
	
	public AppTag()
	{
		
	}
	
	public String toString()
	{
		return "[AppTag id=" + id + " name=" + name + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}