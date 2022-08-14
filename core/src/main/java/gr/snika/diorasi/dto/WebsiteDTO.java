package gr.snika.diorasi.dto;

import java.util.UUID;

public class WebsiteDTO {

	private String domain;
	
	private String name;
	
	private UUID owner_id;
	
	public WebsiteDTO() {}

	public WebsiteDTO(String domain, String name, UUID owner_id) {
		super();
		this.domain = domain;
		this.name = name;
		this.owner_id = owner_id;
	}

	public String getDomain() {
		return domain;
	}

	public String getName() {
		return name;
	}

	public UUID getOwner_id() {
		return owner_id;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOwner_id(UUID owner_id) {
		this.owner_id = owner_id;
	}
	
}
