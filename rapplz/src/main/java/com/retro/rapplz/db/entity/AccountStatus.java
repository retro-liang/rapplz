package com.retro.rapplz.db.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="account_status")
public class AccountStatus extends BaseEntity
{
	public static final String DEFAULT = "PENDING";
	private String name = DEFAULT;
	
	@OneToMany(mappedBy="accountStatus")
    private Set<User> users = new HashSet<User>();
	
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