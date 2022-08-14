package gr.snika.diorasi.entities;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AppUser {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private UUID id;
	
	private String username;
	
	private String password;
	
	private String email;
	
	@Column(columnDefinition = "boolean default true")
	private boolean enabled;
	
	private String role;
	
	public AppUser() {}
	
	public AppUser(String username, String password, String email, boolean enabled, String role) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.enabled = enabled;
		this.role = role;
	}

	public UUID getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Users [username=" + username + ", password=" + password + ", email=" + email + ", enabled=" + enabled
				+ ", role=" + role + "]";
	}
	
	
	public AppUserDetails asUserDetails() {
		return new AppUserDetails(username, password, email, enabled, role);
	}
}
