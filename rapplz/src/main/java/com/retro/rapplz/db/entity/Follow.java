package com.retro.rapplz.db.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@SuppressWarnings("serial")
@Entity
@Table(name="follower_following")
@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true, includeFieldNames=true)
public class Follow extends BaseEntity
{
	@ManyToOne
	@JoinColumn(name="follower_user_id")
	public User follower;
	
	@ManyToOne
	@JoinColumn(name="following_user_id")
	public User following;
}