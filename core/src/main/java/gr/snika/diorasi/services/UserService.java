package gr.snika.diorasi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gr.snika.diorasi.dto.AppUserDTO;
import gr.snika.diorasi.entities.AppUser;
import gr.snika.diorasi.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
		
	public String saveUser(AppUserDTO aud) {
		AppUser user = convertToAppUser(aud);
		userRepository.save(user);
		return "success";
	}
	
	public AppUser convertToAppUser(AppUserDTO aud) {
		
		AppUser user = new AppUser();
		user.setUsername(aud.getUsername());
		user.setPassword(aud.getPassword());
		user.setEnabled(true);
		
		return user;
		
	}

}
