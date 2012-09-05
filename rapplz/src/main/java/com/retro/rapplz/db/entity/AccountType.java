package com.retro.rapplz.db.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Immutable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Immutable
@DynamicInsert
@DynamicUpdate
@Table(name="account_type")
@NamedQueries
({
	@NamedQuery(name = "AccountType.findAll", query = "SELECT t FROM AccountType t"),
	@NamedQuery(name = "AccountType.findById", query = "SELECT t FROM AccountType t WHERE t.id = :id"),
	@NamedQuery(name = "AccountType.findByName", query = "SELECT t FROM AccountType t WHERE t.name = :name")
})
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Data
@EqualsAndHashCode(callSuper=true, exclude={"users"})
@ToString(callSuper=true, includeFieldNames=true, exclude={"users"})
public class AccountType extends BaseEntity implements Serializable
{
	private static final long serialVersionUID = 6088666218993896719L;
	
	public static final String DEFAULT = "RAPPLZ";
	public static final String RAPPLZ = "RAPPLZ";
	public static final String GOOGLE = "GOOGLE";
	public static final String FACEBOOK = "FACEBOOK";
	public static final String TWITTER = "TWITTER";
	
	private String name = DEFAULT;
	
	@OneToMany(mappedBy="accountType")
	@XmlTransient
    private Set<User> users = new HashSet<User>();
}