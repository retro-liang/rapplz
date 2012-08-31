package com.retro.rapplz.db.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

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
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Data
@EqualsAndHashCode(callSuper=true, exclude={"users"})
@ToString(callSuper=true, includeFieldNames=true, exclude={"users"})
public class AccountStatus extends BaseEntity
{
	public static final String DEFAULT = "PENDING";
	public static final String PENDING = "PENDING";
	public static final String ACTIVE = "ACTIVE";
	public static final String INACTIVE = "INACTIVE";
	
	private String name = DEFAULT;
	
	@OneToMany(mappedBy="accountStatus")
    private Set<User> users = new HashSet<User>();
}