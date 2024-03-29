package com.retro.rapplz.db.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@DynamicInsert
@DynamicUpdate
@Table(name="account_role")
@NamedQueries
({
	@NamedQuery(name = "AccountRole.findAll", query = "SELECT s FROM AccountRole s"),
	@NamedQuery(name = "AccountRole.findById", query = "SELECT s FROM AccountRole s WHERE s.id = :id"),
	@NamedQuery(name = "AccountRole.findByName", query = "SELECT s FROM AccountRole s WHERE s.name = :name")
})
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Data
@EqualsAndHashCode(callSuper=true, exclude={"users"})
@ToString(callSuper=true, includeFieldNames=true, exclude={"users"})
public class AccountRole extends BaseEntity implements Serializable
{
	private static final long serialVersionUID = -5934155326162492033L;

	public static final String DEFAULT = "USER";
	public static final String USER = "USER";
	public static final String ADMIN = "ADMIN";
	
	private String name = DEFAULT;
	
	@ManyToMany(mappedBy="accountRoles")
	@XmlTransient
    private Set<User> users = new HashSet<User>();
}