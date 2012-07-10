package com.retro.rapplz.db.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="review")
public class Review extends BaseMessage
{
	@ManyToOne
	@JoinColumn(name="app_id")
	private App app;
	
	@OneToMany(mappedBy="review")
	private Set<ReviewComment> reviewComments = new HashSet<ReviewComment>();
	
	public Review()
	{
		
	}

	public App getApp() {
		return app;
	}

	public void setApp(App app) {
		this.app = app;
	}

	public Set<ReviewComment> getReviewComments() {
		return reviewComments;
	}

	public void setReviewComments(Set<ReviewComment> reviewComments) {
		this.reviewComments = reviewComments;
	}
}