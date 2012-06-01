package com.retro.rapplz.server.datastore.entity;

import java.util.Date;

import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Unindexed;

@Cached
@Entity
@XmlRootElement
public class Activity
{
	@Id
	private String id;
	
	private String type;
	
	private String author;
	
	@Unindexed
	private String content;
	
	@Unindexed
	private Date date;
	
	public Activity()
	{
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
}