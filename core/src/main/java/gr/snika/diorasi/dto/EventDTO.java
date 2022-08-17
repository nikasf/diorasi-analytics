package gr.snika.diorasi.dto;

import java.util.Date;

public class EventDTO {

	private String eventType;
	
	private String eventValue;
	
	private String domain;
	
	private String url;
	
	private String sessionId;
	
	private Date createdAt;
	
	private String referrer;
	
	public EventDTO() {}

	public EventDTO(String eventType, String eventValue, String domain, String url, String sessionId, Date createdAt,
			String referrer) {
		super();
		this.eventType = eventType;
		this.eventValue = eventValue;
		this.domain = domain;
		this.url = url;
		this.sessionId = sessionId;
		this.createdAt = createdAt;
		this.referrer = referrer;
	}

	public String getEventType() {
		return eventType;
	}

	public String getEventValue() {
		return eventValue;
	}

	public String getDomain() {
		return domain;
	}

	public String getUrl() {
		return url;
	}

	public String getSessionId() {
		return sessionId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public String getReferrer() {
		return referrer;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public void setEventValue(String eventValue) {
		this.eventValue = eventValue;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public void setReferrer(String referrer) {
		this.referrer = referrer;
	}

}
