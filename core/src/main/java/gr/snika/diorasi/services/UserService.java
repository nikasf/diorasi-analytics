package gr.snika.diorasi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import gr.snika.diorasi.dto.AppUserDTO;
import gr.snika.diorasi.entities.AppUser;
import gr.snika.diorasi.enums.Role;
import gr.snika.diorasi.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
    private PasswordEncoder bcryptEncoder;
		
	public String saveUser(AppUserDTO aud) {
		AppUser user = convertToAppUser(aud);
		AppUser savedUser = userRepository.save(user);
		return savedUser.getId().toString();
	}
	
	public AppUser convertToAppUser(AppUserDTO aud) {
		AppUser user = new AppUser();
		user.setUsername(aud.getUsername());
		user.setPassword(bcryptEncoder.encode(aud.getPassword()));
		user.setEmail(aud.getEmail());
		user.setEnabled(true);
		user.setRole(Role.USER.name());
		
		return user;
		
	}

}
