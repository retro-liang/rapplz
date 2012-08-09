package com.retro.rapplz.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@SuppressWarnings("serial")
@Entity
@Table(name="activity")
@Data
@EqualsAndHashCode(callSuper=true, exclude={"content"})
@ToString(callSuper=true, includeFieldNames=true, exclude={"content"})
public class Activity extends BaseEntity
{
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@Enumerated(EnumType.STRING)
	@Column(name="activity_type")
	private ActivityType activityType;
	
	@Enumerated(EnumType.STRING)
	@Column(name="activity_result")
	private ActivityResult activityResult;
	
	private String content;
}