package com.retro.rapplz.db.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="user")
@NamedQueries
({
	@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
	@NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.id = :id"),
	@NamedQuery(name = "User.findByFirstName", query = "SELECT u FROM User u WHERE u.firstName = :firstName"),
	@NamedQuery(name = "User.findByLastName", query = "SELECT u FROM User u WHERE u.lastName = :lastName"),
	@NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email"),
	@NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password")
})
public class User extends BaseEntity implements Serializable
{
	private static final long serialVersionUID = 3295552597219824938L;

	@NotBlank
	@Length(min=6, max=50)
	@Email
	private String email;
	
	@NotBlank
	@Length(min=6, max=40)
	private String password;
	
	@NotBlank
	@Length(min=3, max=30)
	@Column(name = "first_name")
	private String firstName;
	
	@NotBlank
	@Length(min=3, max=30)
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "avatar")
	private String avatar;
	
	@ManyToOne
	@JoinColumn(name="account_type_id")
	private AccountType accountType;
	
	@ManyToMany
    @JoinTable
    (
    	name="user_account_role",
        joinColumns={@JoinColumn(name="user_id")},
        inverseJoinColumns={@JoinColumn(name="account_role_id")}
    )
	private Set<AccountRole> accountRoles = new HashSet<AccountRole>();
	
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
	
	@Override
	public int hashCode()
	{
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object)
	{
		//Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof User))
		{
			return false;
		}
		User other = (User) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
		{
			return false;
		}
		return true;
	}

	@Override
	public String toString()
	{
		return "User[id=" + id + " email=" + email + " type=" + accountType + " status=" + accountStatus + "]";
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

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Set<AccountRole> getAccountRoles() {
		return accountRoles;
	}

	public void setAccountRoles(Set<AccountRole> accountRoles) {
		this.accountRoles = accountRoles;
	}
}