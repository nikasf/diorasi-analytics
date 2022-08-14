package gr.snika.diorasi.entities;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="website")
public class Website {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private UUID id;
	
	private String domain;
	
	private String name;
	
	@ManyToOne
	@JoinColumn(name="owner_id", nullable=false)
	private AppUser owner;
	
	public Website() {}

	public Website(String domain, String name, AppUser owner) {
		super();
		this.domain = domain;
		this.name = name;
		this.owner = owner;
	}

	public String getDomain() {
		return domain;
	}

	public String getName() {
		return name;
	}

	public AppUser getOwner() {
		return owner;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOwner(AppUser owner) {
		this.owner = owner;
	}
	
}
