package gr.snika.diorasi.dto;

import java.util.Date;

public class WebsiteDTO {

	private String domain;
	
	private String name;
	
	private Date createdAt;
	
	public WebsiteDTO() {}

	public WebsiteDTO(String domain, String name, Date createdAt) {
		super();
		this.domain = domain;
		this.name = name;
		this.createdAt = createdAt;
	}

	public String getDomain() {
		return domain;
	}

	public String getName() {
		return name;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public void setName(String name) {
		this.name = name;
	}	

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
}
