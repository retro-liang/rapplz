package com.retro.rapplz.db.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="account_role")
@NamedQueries
({
	@NamedQuery(name = "AccountRole.findAll", query = "SELECT s FROM AccountRole s"),
	@NamedQuery(name = "AccountRole.findById", query = "SELECT s FROM AccountRole s WHERE s.id = :id"),
	@NamedQuery(name = "AccountRole.findByName", query = "SELECT s FROM AccountRole s WHERE s.name = :name")
})
public class AccountRole extends BaseEntity implements Serializable
{
	private static final long serialVersionUID = -5934155326162492033L;

	public static final String DEFAULT = "ROLE_USER";
	private String name = DEFAULT;
	
	@ManyToMany(mappedBy="accountRoles")	
    private Set<User> users = new HashSet<User>();
	
	public AccountRole()
	{
		
	}
	
	@Override
	public int hashCode()
	{
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object)
	{
		//Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof AccountRole))
		{
			return false;
		}
		AccountRole other = (AccountRole) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
		{
			return false;
		}
		return true;
	}

	@Override
	public String toString()
	{
		return "com.retro.rapplz.entity.User[id=" + id + "]";
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