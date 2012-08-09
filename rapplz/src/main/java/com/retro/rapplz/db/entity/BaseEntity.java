package com.retro.rapplz.db.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@SuppressWarnings("serial")
@MappedSuperclass
@ToString(exclude={"jdoDetachedState", "createdDate"})
@EqualsAndHashCode(exclude={"jdoDetachedState", "createdDate"})
public abstract class BaseEntity implements Serializable 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Getter
	protected Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_date")
	@Getter
	private final Date createdDate = new Date();
	
	@Transient
	protected Object[] jdoDetachedState;
}