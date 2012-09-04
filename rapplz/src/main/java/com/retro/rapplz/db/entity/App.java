package com.retro.rapplz.db.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@DynamicInsert
@DynamicUpdate
@Table(name="app")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@EqualsAndHashCode(callSuper=true, exclude={"os", "devices", "categories", "iconUrl", "recommendations", "appComments", "reviews", "tags", "users"})
@ToString(callSuper=true, includeFieldNames=true, exclude={"os", "devices", "categories", "iconUrl", "recommendations", "appComments", "reviews", "tags", "users"})
public class App extends BaseEntity implements Serializable
{
	private static final long serialVersionUID = -3240048296215090416L;

	@Column(name="raw_id")
	private String rawId;
	
	private String name;
	
	@ManyToOne
	@JoinColumn(name="os_id")
	private OS os;

	@ManyToMany
	@Cascade({CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "app_device", joinColumns = {@JoinColumn(name = "app_id")}, inverseJoinColumns = {@JoinColumn(name = "device_id")})
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private Set<Device> devices = new HashSet<Device>();
	
	@ManyToMany(fetch = FetchType.EAGER)
	@Cascade({CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "app_category", joinColumns = {@JoinColumn(name = "app_id")}, inverseJoinColumns = {@JoinColumn(name = "category_id")})
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private Set<Category> categories = new HashSet<Category>();
	
	@Column(name = "icon_url")
	private String iconUrl;
	
	@OneToMany(mappedBy="app")
	@BatchSize(size = 10)
	private Set<Recommendation> recommendations = new HashSet<Recommendation>();
	
	@OneToMany(mappedBy="app")
	@BatchSize(size = 10)
	private Set<AppComment> appComments = new HashSet<AppComment>();
	
	@OneToMany(mappedBy="app")
	@BatchSize(size = 10)
	private Set<Review> reviews = new HashSet<Review>();
	
	@OneToMany(mappedBy="app")
	@BatchSize(size = 10)
	private Set<Tag> tags = new HashSet<Tag>();
	
	@ManyToMany(mappedBy="apps")
	@BatchSize(size = 10)
    private Set<User> users = new HashSet<User>();
	
	/*
	 * The following attributes are not necessarily stored in database, they can be retrieved by looking up app store (ios) for now.
	 * 
	private String description;
	
	@Column(name="company_id")
	private String companyId;
	
	@Column(name="company_name")
	private String companyName;

	@Column(name = "app_store_url")
	private String appStoreUrl;
	
	private BigDecimal price;
	
	private String version;
	
	@Column(name="company_url")
	private String companyUrl;
	
	@Column(name="release_date")
	private String releaseDate;
	
	@Column(name="app_store_rating")
	private String appStoreRating;
	
	@Column(name="app_store_rating_count")
	private String appStoreRatingCount;
	
	@OneToMany(mappedBy="app")
	private Set<Screenshot> screenshots = new HashSet<Screenshot>();*/
}