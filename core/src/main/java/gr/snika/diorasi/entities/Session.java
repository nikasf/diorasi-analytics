package gr.snika.diorasi.entities;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name="session")
public class Session {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private UUID id;
	
	@Column(name = "created_at")
	@CreationTimestamp
	private Date createdAt;
	
	private String browser;
	
	private String os;
	
	private String device;
	
	private String resolution;
	
	private String language;
	
	private String country;
	
	@ManyToOne
	@JoinColumn(name="website_id", nullable=false)
	private Website website;
	
	@OneToMany(mappedBy = "session", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Event> events;
	
	public Session () {}

	public Session(Date createdAt, String browser, String os, String device, String resolution, String language,
			String country) {
		super();
		this.createdAt = createdAt;
		this.browser = browser;
		this.os = os;
		this.device = device;
		this.resolution = resolution;
		this.language = language;
		this.country = country;
	}

	public UUID getId() {
		return id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public String getBrowser() {
		return browser;
	}

	public String getOs() {
		return os;
	}

	public String getDevice() {
		return device;
	}

	public String getResolution() {
		return resolution;
	}

	public String getLanguage() {
		return language;
	}

	public String getCountry() {
		return country;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Website getWebsite() {
		return website;
	}

	public Set<Event> getEvents() {
		return events;
	}

	public void setWebsite(Website website) {
		this.website = website;
	}

	public void setEvents(Set<Event> events) {
		this.events = events;
	}

}
