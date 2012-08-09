package com.retro.rapplz.db.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@SuppressWarnings("serial")
@Entity
@Table(name="category")
@Data
@EqualsAndHashCode(callSuper=true, exclude={"apps"})
@ToString(callSuper=true, includeFieldNames=true, exclude={"apps"})
public class Category extends BaseEntity
{
	private String name;
	
	@ManyToMany
	(
        mappedBy = "categories",
        targetEntity = App.class
    )
	@Cascade(CascadeType.SAVE_UPDATE)
	private Set<App> apps = new HashSet<App>();
}