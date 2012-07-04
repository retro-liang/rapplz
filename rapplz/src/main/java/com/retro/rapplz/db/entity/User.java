package com.retro.rapplz.db.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.Email;

@SuppressWarnings("serial")
@Entity
@Table(name="user")
public class User extends BaseEntity
{
	@Email
	private String email;
	
	private String password;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@ManyToOne
	@JoinColumn(name="account_type_id")
	private AccountType accountType;
	
	@ManyToOne
	@JoinColumn(name="account_status_id")
	private AccountStatus accountStatus;
	
	@OneToMany(mappedBy="user")
	private Set<Activity> activities = new HashSet<Activity>();
	
	@OneToMany(mappedBy="author")
	private Set<AppComment> appComments = new HashSet<AppComment>();
	
	@OneToMany(mappedBy="author")
	private Set<Review> reviews = new HashSet<Review>();
	
	@OneToMany(mappedBy="author")
	private Set<ReviewComment> reviewComments = new HashSet<ReviewComment>();
	
	@ManyToMany
    @JoinTable
    (
    	name="follower_following",
        joinColumns={@JoinColumn(name="follower_user_id")},
        inverseJoinColumns={@JoinColumn(name="following_user_id")}
    )
	@Cascade(CascadeType.ALL)
    private Set<User> followers = new HashSet<User>();
	
	@ManyToMany(mappedBy="followers")
    private Set<User> followings = new HashSet<User>();
	
	@OneToMany(mappedBy="fromUser")
	private Set<Recommendation> sentRecommendation = new HashSet<Recommendation>();
	
	@OneToMany(mappedBy="toUser")
	private Set<Recommendation> receivedRecommendation = new HashSet<Recommendation>();
	
	@OneToMany(mappedBy="author")
	private Set<Tag> tags = new HashSet<Tag>();
	
	public User()
	{
		
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public AccountStatus getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(AccountStatus accountStatus) {
		this.accountStatus = accountStatus;
	}

	public Set<Activity> getActivities() {
		return activities;
	}

	public void setActivities(Set<Activity> activities) {
		this.activities = activities;
	}

	public Set<User> getFollowers() {
		return followers;
	}

	public void setFollowers(Set<User> followers) {
		this.followers = followers;
	}

	public Set<User> getFollowings() {
		return followings;
	}

	public void setFollowings(Set<User> followings) {
		this.followings = followings;
	}

	public Set<Recommendation> getSentRecommendation() {
		return sentRecommendation;
	}

	public void setSentRecommendation(Set<Recommendation> sentRecommendation) {
		this.sentRecommendation = sentRecommendation;
	}

	public Set<Recommendation> getReceivedRecommendation() {
		return receivedRecommendation;
	}

	public void setReceivedRecommendation(Set<Recommendation> receivedRecommendation) {
		this.receivedRecommendation = receivedRecommendation;
	}

	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	public Set<AppComment> getAppComments() {
		return appComments;
	}

	public void setAppComments(Set<AppComment> appComments) {
		this.appComments = appComments;
	}

	public Set<Review> getReviews() {
		return reviews;
	}

	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}

	public Set<ReviewComment> getReviewComments() {
		return reviewComments;
	}

	public void setReviewComments(Set<ReviewComment> reviewComments) {
		this.reviewComments = reviewComments;
	}
}