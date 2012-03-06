package com.retro.rapplz.server.datastore.entity;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Unindexed;

@Cached
@Entity
public class Role
{
	@Id
	private Long id;
	
	private String name = "DEFAULT";
	
	@Unindexed
	private String note;
	
	public Role()
	{
		
	}
}
