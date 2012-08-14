package com.retro.rapplz.db.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name="account_role")
@NamedQueries
({
	@NamedQuery(name = "AccountRole.findAll", query = "SELECT s FROM AccountRole s"),
	@NamedQuery(name = "AccountRole.findById", query = "SELECT s FROM AccountRole s WHERE s.id = :id"),
	@NamedQuery(name = "AccountRole.findByName", query = "SELECT s FROM AccountRole s WHERE s.name = :name")
})
@Data
@EqualsAndHashCode(callSuper=true, exclude={"users"})
@ToString(callSuper=true, includeFieldNames=true, exclude={"users"})
public class AccountRole extends BaseEntity implements Serializable
{
	private static final long serialVersionUID = -5934155326162492033L;

	public static final String DEFAULT = "ROLE_USER";
	public static final String USER = "ROLE_USER";
	public static final String ADMIN = "ROLE_ADMIN";
	
	private String name = DEFAULT;
	
	@ManyToMany(mappedBy="accountRoles")	
    private Set<User> users = new HashSet<User>();
}