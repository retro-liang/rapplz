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
@Table(name="account_status")
@NamedQueries
({
	@NamedQuery(name = "AccountStatus.findAll", query = "SELECT s FROM AccountStatus s"),
	@NamedQuery(name = "AccountStatus.findById", query = "SELECT s FROM AccountStatus s WHERE s.id = :id"),
	@NamedQuery(name = "AccountStatus.findByName", query = "SELECT s FROM AccountStatus s WHERE s.name = :name")
})
@Data
@EqualsAndHashCode(callSuper=true, exclude={"users"})
@ToString(callSuper=true, includeFieldNames=true, exclude={"users"})
public class AccountStatus extends BaseEntity
{
	public static final String DEFAULT = "PENDING";
	private String name = DEFAULT;
	
	@OneToMany(mappedBy="accountStatus")
    private Set<User> users = new HashSet<User>();
}