package com.retro.rapplz.db.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@SuppressWarnings("serial")
@Entity
@Table(name="review")
@Data
@EqualsAndHashCode(callSuper=true, exclude={"reviewComments"})
@ToString(callSuper=true, includeFieldNames=true, exclude={"reviewComments"})
public class Review extends BaseMessage
{
	@ManyToOne
	@JoinColumn(name="app_id")
	private App app;
	
	@OneToMany(mappedBy="review")
	private Set<ReviewComment> reviewComments = new HashSet<ReviewComment>();
}