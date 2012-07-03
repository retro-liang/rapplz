package com.retro.rapplz.db.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="account_status")
public class AccountStatus extends BaseEntity
{
	private String name;
	
	public AccountStatus()
	{
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}