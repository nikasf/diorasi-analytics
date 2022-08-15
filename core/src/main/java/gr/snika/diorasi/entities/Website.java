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

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name="website")
public class Website {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private UUID id;
	
	private String domain;
	
	private String name;
	
	@Column(name = "created_at")
	@CreationTimestamp
	private Date createdAt;
	
	@ManyToOne
	@JoinColumn(name="owner_id", nullable=false)
	private AppUser owner;
	
	public Website() {}

	public Website(String domain, String name, Date createdAt, AppUser owner) {
		super();
		this.domain = domain;
		this.name = name;
		this.createdAt = createdAt;
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

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
}
