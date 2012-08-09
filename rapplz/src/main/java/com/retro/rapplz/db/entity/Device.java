package com.retro.rapplz.db.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@SuppressWarnings("serial")
@Entity
@Table(name="device")
@Data
@EqualsAndHashCode(callSuper=true, exclude={"apps"})
@ToString(callSuper=true, includeFieldNames=true, exclude={"apps"})
public class Device extends BaseEntity
{
	private String name;
	
	@ManyToMany(mappedBy="devices")
	private Set<App> apps = new HashSet<App>();
}