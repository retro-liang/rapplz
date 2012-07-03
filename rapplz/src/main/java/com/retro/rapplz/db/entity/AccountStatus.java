package com.retro.rapplz.db.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="account_status")
public class AccountStatus extends BaseEntity
{
	private String name;
	
	@OneToMany(mappedBy="accountStatus")
    private Set<User> users;
	
	public AccountStatus()
	{
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
}