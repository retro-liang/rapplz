package com.retro.rapplz.db.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@SuppressWarnings("serial")
@Entity
public class ReviewComment extends BaseMessage
{
	@ManyToOne
	@JoinColumn(name="review_id")
	private Review review;
	
	public ReviewComment()
	{
		
	}

	public Review getReview() {
		return review;
	}

	public void setReview(Review review) {
		this.review = review;
	}
}