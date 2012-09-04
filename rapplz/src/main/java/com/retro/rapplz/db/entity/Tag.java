package com.retro.rapplz.db.entity;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
@Table(name="tag")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true, includeFieldNames=true)
public class Tag extends BaseEntity implements Serializable
{
	private static final long serialVersionUID = -2408853020744017232L;

	@ManyToOne
	@JoinColumn(name="tag_name_id")
	private TagName tagName;
	
	@ManyToOne
	@JoinColumn(name="author_user_id")
	private User author;
	
	@ManyToOne
	@JoinColumn(name="app_id")
	private App app;
}