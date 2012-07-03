package com.retro.rapplz.db.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="account_type")
public class AccountType extends BaseEntity
{
	private String name;
	
	public AccountType()
	{
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}