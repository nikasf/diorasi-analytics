package gr.snika.diorasi.entities;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="event")
public class Event {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private UUID id;
	
	@Column(name = "session_id")
	private String sessionId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	private Date createdAt;
	
	private String url;
	
	@Column(name = "event_type")
	private String eventType;
	
	@Column(name = "event_value")
	private String eventValue;
	
	@ManyToOne
	@JoinColumn(name="website_id", nullable=false)
	private Website website;
	
	private String referrer;
	
	public Event() {}
	
	public Event(String sessionId, Date createdAt, String url, String eventType, String eventValue, String referrer) {
		super();
		this.sessionId = sessionId;
		this.createdAt = createdAt;
		this.url = url;
		this.eventType = eventType;
		this.eventValue = eventValue;
		this.referrer = referrer;
	}

	public UUID getId() {
		return id;
	}

	public String getSessionId() {
		return sessionId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public String getUrl() {
		return url;
	}

	public String getEventType() {
		return eventType;
	}

	public String getEventValue() {
		return eventValue;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public void setEventValue(String eventValue) {
		this.eventValue = eventValue;
	}

	public Website getWebsite() {
		return website;
	}

	public String getReferrer() {
		return referrer;
	}

	public void setWebsite(Website website) {
		this.website = website;
	}

	public void setReferrer(String referrer) {
		this.referrer = referrer;
	}
	
}
