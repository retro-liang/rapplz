package com.retro.rapplz.db.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
@Table(name="activity")
@Data
@EqualsAndHashCode(callSuper=true, exclude={"content"})
@ToString(callSuper=true, includeFieldNames=true, exclude={"content"})
public class Activity extends BaseEntity implements Serializable
{
	private static final long serialVersionUID = -7121669137734151216L;

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