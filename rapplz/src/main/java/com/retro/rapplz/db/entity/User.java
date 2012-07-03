package com.retro.rapplz.db.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
	private Set<BaseMessage> messages = new HashSet<BaseMessage>();
	
	@OneToMany(mappedBy="follower")
	private Set<User> followers = new HashSet<User>();
	
	@OneToMany(mappedBy="following")
	private Set<User> followerings = new HashSet<User>();
	
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

	public Set<BaseMessage> getMessages() {
		return messages;
	}

	public void setMessages(Set<BaseMessage> messages) {
		this.messages = messages;
	}

	public Set<User> getFollowers() {
		return followers;
	}

	public void setFollowers(Set<User> followers) {
		this.followers = followers;
	}

	public Set<User> getFollowerings() {
		return followerings;
	}

	public void setFollowerings(Set<User> followerings) {
		this.followerings = followerings;
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
}