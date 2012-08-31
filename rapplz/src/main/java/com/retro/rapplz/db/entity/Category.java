package com.retro.rapplz.db.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@SuppressWarnings("serial")
@Entity
@Table(name="category")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Data
@EqualsAndHashCode(callSuper=true, exclude={"apps"})
@ToString(callSuper=true, includeFieldNames=true, exclude={"apps"})
public class Category extends BaseEntity
{
	private String name;
	
	@ManyToMany(mappedBy="categories")
	private Set<App> apps = new HashSet<App>();
}