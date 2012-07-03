package com.retro.rapplz.db.entity;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="app")
public class App extends BaseEntity
{
	private String name;
	
	private String description;
	
	@ManyToOne
	@JoinColumn(name="device_id")
	private Device device;
	
	@ManyToOne
	@JoinColumn(name="os_id")
	private OS os;
	
	@ManyToOne
	@JoinColumn(name="app_category_id")
	private AppCategory appCategory;
	
	@Column(name = "icon_url")
	private String iconUrl;
	
	@Column(name = "app_store_url")
	private String appStoreUrl;
	
	private String price;
	
	@ElementCollection
	@CollectionTable(name="app_screenshot", joinColumns=@JoinColumn(name="app_id"))
	@Column(name="screen_shot_url")
	private List<String> screenShotUrls;
	
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

	public List<String> getScreenShotUrls() {
		return screenShotUrls;
	}

	public void setScreenShotUrls(List<String> screenShotUrls) {
		this.screenShotUrls = screenShotUrls;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public AppCategory getAppCategory() {
		return appCategory;
	}

	public void setAppCategory(AppCategory appCategory) {
		this.appCategory = appCategory;
	}
}