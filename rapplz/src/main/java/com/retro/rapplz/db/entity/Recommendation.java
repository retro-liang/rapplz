package com.retro.rapplz.db.entity;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@SuppressWarnings("serial")
@Entity
@Table(name="recommendation")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Data
@EqualsAndHashCode(callSuper=true, exclude={"message"})
@ToString(callSuper=true, includeFieldNames=true, exclude={"message"})
public class Recommendation extends BaseEntity
{
	@ManyToOne
	@JoinColumn(name="app_id")
	private App app;
	
	@ManyToOne
	@JoinColumn(name="from_user_id")
	private User fromUser;
	
	@ManyToOne
	@JoinColumn(name="to_user_id")
	private User toUser;
	
	private String message;
}