package gr.snika.diorasi.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gr.snika.diorasi.dto.WebsiteDTO;
import gr.snika.diorasi.entities.AppUser;
import gr.snika.diorasi.entities.Website;
import gr.snika.diorasi.repositories.UserRepository;
import gr.snika.diorasi.repositories.WebsiteRepository;

@Service
public class WebsiteService {
	
	@Autowired
	WebsiteRepository websiteRepository;
	
	@Autowired
	UserRepository userRepository;
		
	public void saveWebsite(WebsiteDTO websiteDto) {
		Website website = convertToWebsite(websiteDto);
		websiteRepository.save(website);
	}
	
	public Website convertToWebsite(WebsiteDTO dto) {
		
		Website website = new Website();
		website.setDomain(dto.getDomain());
		website.setName(dto.getName());
		Optional<AppUser> optional = userRepository.findById(dto.getOwner_id());
		optional.ifPresent(user -> website.setOwner(user));
		//FIXME Create a custom exception
		optional.orElseThrow(() -> new RuntimeException("The website must have a valid owner."));
		
		return website;
	}

}
