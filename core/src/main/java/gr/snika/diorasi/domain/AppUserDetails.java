package gr.snika.diorasi.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import gr.snika.diorasi.entities.AppUser;

public class AppUserDetails implements UserDetails {
	
	private static final long serialVersionUID = 8597925794504973138L;
	
	AppUser user;
	
	public AppUserDetails(AppUser user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority sga = new SimpleGrantedAuthority(user.getRole());
//		SimpleGrantedAuthority sga = new SimpleGrantedAuthority("ADMIN");
		List<SimpleGrantedAuthority> sgaList = new ArrayList<>();
		sgaList.add(sga);
		return sgaList;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}
	
	@Override
	public boolean isEnabled() {
		return user.isEnabled();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

}
