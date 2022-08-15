package gr.snika.diorasi.services;

import java.util.ArrayList;
import java.util.List;

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
		
	public void saveWebsite(WebsiteDTO websiteDto, String username) {
		Website website = convertToWebsite(websiteDto);
		AppUser user = getUser(username);
		website.setOwner(user);
		websiteRepository.save(website);
	}
	
	private AppUser getUser(String username) {
		return userRepository.findByUsername(username);
	}

	public List<WebsiteDTO> getAllWebsites(String username){
		List<WebsiteDTO> dtos = new ArrayList<>();
		AppUser user = getUser(username);
		Iterable<Website> websites = websiteRepository.findAllByOwnerId(user);
		websites.forEach(website -> dtos.add(convertToWebsiteDTO(website)));
		
		return dtos;
	}
	
	
	public Website convertToWebsite(WebsiteDTO dto) {
		Website website = new Website();
		website.setDomain(dto.getDomain());
		website.setName(dto.getName());
		website.setCreatedAt(dto.getCreatedAt());
		return website;
	}
	
	public WebsiteDTO convertToWebsiteDTO(Website website) {
		WebsiteDTO websiteDTO = new WebsiteDTO();
		websiteDTO.setDomain(website.getDomain());
		websiteDTO.setName(website.getName());
		websiteDTO.setCreatedAt(website.getCreatedAt());
		return websiteDTO;
	}

}
