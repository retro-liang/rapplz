package com.retro.rapplz.db.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@SuppressWarnings("serial")
@Entity
@Table(name="account_type")
@NamedQueries
({
	@NamedQuery(name = "AccountType.findAll", query = "SELECT t FROM AccountType t"),
	@NamedQuery(name = "AccountType.findById", query = "SELECT t FROM AccountType t WHERE t.id = :id"),
	@NamedQuery(name = "AccountType.findByName", query = "SELECT t FROM AccountType t WHERE t.name = :name")
})
@Data
@EqualsAndHashCode(callSuper=true, exclude={"users"})
@ToString(callSuper=true, includeFieldNames=true, exclude={"users"})
public class AccountType extends BaseEntity
{
	public static final String DEFAULT = "RAPPLZ";
	private String name = DEFAULT;
	
	@OneToMany(mappedBy="accountType")
    private Set<User> users = new HashSet<User>();
}