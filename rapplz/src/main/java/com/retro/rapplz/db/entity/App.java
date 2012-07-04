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

@SuppressWarnings("serial")
@Entity
@Table(name="app")
public class App extends BaseEntity
{
	@Column(name="raw_id")
	private String rawId;
	
	private String name;
	
	private String description;

	@ManyToOne
	@JoinColumn(name="os_id")
	private OS os;

	@ManyToMany
	@JoinTable
    (
        name="app_device",
        joinColumns={@JoinColumn(name="app_id")},
        inverseJoinColumns={@JoinColumn(name="device_id")}
    )
	@Cascade(CascadeType.ALL)
	private Set<Device> devices = new HashSet<Device>();
	
	@ManyToMany
	(
		targetEntity=Category.class
	)
    @JoinTable
    (
        name="app_category",
        joinColumns=@JoinColumn(name="app_id"),
        inverseJoinColumns=@JoinColumn(name="category_id")
    )
	@Cascade(CascadeType.SAVE_UPDATE)
	private Set<Category> categories = new HashSet<Category>();
	
	@Column(name = "icon_url")
	private String iconUrl;
	
	@Column(name = "app_store_url")
	private String appStoreUrl;
	
	private String price;
	
	private String version;
	
	@Column(name="company_id")
	private String companyId;
	
	@Column(name="company_name")
	private String companyName;
	
	@Column(name="company_url")
	private String companyUrl;
	
	@Column(name="release_date")
	private String releaseDate;
	
	@Column(name="app_store_rating")
	private String appStoreRating;
	
	@Column(name="app_store_rating_count")
	private String appStoreRatingCount;
	
	@OneToMany(mappedBy="app")
	private Set<Screenshot> screenshots = new HashSet<Screenshot>();
	
	@OneToMany(mappedBy="app")
	private Set<Recommendation> recommendations = new HashSet<Recommendation>();
	
	@OneToMany(mappedBy="app")
	private Set<AppComment> appComments = new HashSet<AppComment>();
	
	@OneToMany(mappedBy="app")
	private Set<Review> reviews = new HashSet<Review>();
	
	@OneToMany(mappedBy="app")
	private Set<Tag> tags = new HashSet<Tag>();
	
	public App()
	{
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public OS getOs() {
		return os;
	}

	public void setOs(OS os) {
		this.os = os;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getAppStoreUrl() {
		return appStoreUrl;
	}

	public void setAppStoreUrl(String appStoreUrl) {
		this.appStoreUrl = appStoreUrl;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Device> getDevices() {
		return devices;
	}

	public void setDevices(Set<Device> devices) {
		this.devices = devices;
	}

	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	public String getRawId() {
		return rawId;
	}

	public void setRawId(String rawId) {
		this.rawId = rawId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyUrl() {
		return companyUrl;
	}

	public void setCompanyUrl(String companyUrl) {
		this.companyUrl = companyUrl;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getAppStoreRating() {
		return appStoreRating;
	}

	public void setAppStoreRating(String appStoreRating) {
		this.appStoreRating = appStoreRating;
	}

	public String getAppStoreRatingCount() {
		return appStoreRatingCount;
	}

	public void setAppStoreRatingCount(String appStoreRatingCount) {
		this.appStoreRatingCount = appStoreRatingCount;
	}

	public Set<Screenshot> getScreenshots() {
		return screenshots;
	}

	public void setScreenshots(Set<Screenshot> screenshots) {
		this.screenshots = screenshots;
	}

	public Set<AppComment> getAppComments() {
		return appComments;
	}

	public void setAppComments(Set<AppComment> appComments) {
		this.appComments = appComments;
	}

	public Set<Recommendation> getRecommendations() {
		return recommendations;
	}

	public void setRecommendations(Set<Recommendation> recommendations) {
		this.recommendations = recommendations;
	}

	public Set<Review> getReviews() {
		return reviews;
	}

	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}

	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}
}