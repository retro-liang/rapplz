package com.retro.rapplz.db.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "user")
@NamedQueries
({
	@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
	@NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.id = :id"),
	@NamedQuery(name = "User.findByFirstName", query = "SELECT u FROM User u WHERE u.firstName = :firstName"),
	@NamedQuery(name = "User.findByLastName", query = "SELECT u FROM User u WHERE u.lastName = :lastName"),
	@NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email"),
	@NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password")
})
@Data
@EqualsAndHashCode(callSuper=true, exclude={"password", "avatar", "accountType", "accountRoles", "accountStatus", "apps", "activities", "appComments", "reviews", "reviewComments", "followers", "followings", "sentRecommendations", "receivedRecommendations", "tags"})
@ToString(callSuper=true, includeFieldNames=true, exclude={"password", "lastName", "avatar", "apps", "activities", "appComments", "reviews", "reviewComments", "followers", "followings", "sentRecommendations", "receivedRecommendations", "tags"})
public class User extends BaseEntity implements UserDetails, Serializable
{
	private static final long serialVersionUID = 3295552597219824938L;
	
	@Column(name = "federal_id")
	private String federalId;

	@NotBlank
	@Length(min = 6, max = 50)
	@Email
	private String email;

	private String password;

	@NotBlank
	@Length(min = 3, max = 30)
	@Column(name = "first_name")
	private String firstName;

	@NotBlank
	@Length(min = 3, max = 30)
	@Column(name = "last_name")
	private String lastName;

	@Column(name = "avatar")
	private String avatar;

	@ManyToOne
	@JoinColumn(name = "account_type_id")
	private AccountType accountType;

	@ManyToMany(fetch = FetchType.EAGER)
	@Cascade({CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "user_account_role", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "account_role_id")})
	private Set<AccountRole> accountRoles = new HashSet<AccountRole>();

	@ManyToOne
	@JoinColumn(name = "account_status_id")
	private AccountStatus accountStatus;
	
	@ManyToMany
	@JoinTable(name = "user_app", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "app_id")})
	@LazyCollection(LazyCollectionOption.EXTRA)
	private Set<App> apps = new HashSet<App>();

	@OneToMany(mappedBy = "user")
	private Set<Activity> activities = new HashSet<Activity>();

	@OneToMany(mappedBy = "author")
	@LazyCollection(LazyCollectionOption.EXTRA)
	private Set<AppComment> appComments = new HashSet<AppComment>();

	@OneToMany(mappedBy = "author")
	@LazyCollection(LazyCollectionOption.EXTRA)
	private Set<Review> reviews = new HashSet<Review>();

	@OneToMany(mappedBy = "author")
	@LazyCollection(LazyCollectionOption.EXTRA)
	private Set<ReviewComment> reviewComments = new HashSet<ReviewComment>();

	@ManyToMany
	@JoinTable(name = "follower_following", joinColumns = { @JoinColumn(name = "follower_user_id") }, inverseJoinColumns = { @JoinColumn(name = "following_user_id") })
	@Cascade(CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	private Set<User> followers = new HashSet<User>();

	@ManyToMany(mappedBy = "followers")
	@LazyCollection(LazyCollectionOption.EXTRA)
	private Set<User> followings = new HashSet<User>();

	@OneToMany(mappedBy = "fromUser")
	@LazyCollection(LazyCollectionOption.EXTRA)
	private Set<Recommendation> sentRecommendations = new HashSet<Recommendation>();

	@OneToMany(mappedBy = "toUser")
	@LazyCollection(LazyCollectionOption.EXTRA)
	private Set<Recommendation> receivedRecommendations = new HashSet<Recommendation>();

	@OneToMany(mappedBy = "author")
	@LazyCollection(LazyCollectionOption.EXTRA)
	private Set<Tag> tags = new HashSet<Tag>();

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities()
	{
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		return authorities;
	}

	@Override
	public String getUsername()
	{
		return email;
	}

	@Override
	public boolean isAccountNonExpired()
	{
		return true;
	}

	@Override
	public boolean isAccountNonLocked()
	{
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired()
	{
		return true;
	}

	@Override
	public boolean isEnabled()
	{
		return true;
	}
}