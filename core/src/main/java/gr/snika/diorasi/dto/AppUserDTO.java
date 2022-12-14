package gr.snika.diorasi.dto;

public class AppUserDTO {
	
	private String username;
	
	private String password;
	
	private String confirmPassword;
	
	private String email;
	
	public AppUserDTO() {}

	
	public AppUserDTO(String username, String password, String confirmPassword, String email) {
		super();
		this.username = username;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
	
}
