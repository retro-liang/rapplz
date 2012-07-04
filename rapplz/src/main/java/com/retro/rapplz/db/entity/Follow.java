package com.retro.rapplz.db.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="follower_following")
public class Follow extends BaseEntity
{
	@ManyToOne
	@JoinColumn(name="follower_user_id")
	public User follower;
	
	@ManyToOne
	@JoinColumn(name="following_user_id")
	public User following;
	
	public Follow()
	{
		
	}

	public User getFollower() {
		return follower;
	}

	public void setFollower(User follower) {
		this.follower = follower;
	}

	public User getFollowing() {
		return following;
	}

	public void setFollowing(User following) {
		this.following = following;
	}
}