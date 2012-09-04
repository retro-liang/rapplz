package com.retro.rapplz.db.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="review")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Data
@EqualsAndHashCode(callSuper=true, exclude={"reviewComments"})
@ToString(callSuper=true, includeFieldNames=true, exclude={"reviewComments"})
public class Review extends BaseMessage implements Serializable
{
	private static final long serialVersionUID = -6926392957568188460L;

	@ManyToOne
	@JoinColumn(name="app_id")
	private App app;
	
	@OneToMany(mappedBy="review")
	private Set<ReviewComment> reviewComments = new HashSet<ReviewComment>();
}