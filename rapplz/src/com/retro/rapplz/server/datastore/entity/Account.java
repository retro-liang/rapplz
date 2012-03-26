package com.retro.rapplz.server.datastore.entity;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Entity;

@Cached
@Entity
public class Account
{
	@Id
	private Long id;
	
	private String username;
	
	private String password;
	
	private Key<Role> role;
	
	private Key<Status> status;
	
	private String federalType;
	
	public Account()
	{
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Key<Role> getRole() {
		return role;
	}

	public void setRole(Key<Role> role) {
		this.role = role;
	}

	public Key<Status> getStatus() {
		return status;
	}

	public void setStatus(Key<Status> status) {
		this.status = status;
	}

	public String getFederalType() {
		return federalType;
	}

	public void setFederalType(String federalType) {
		this.federalType = federalType;
	}
}