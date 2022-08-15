package gr.snika.diorasi.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gr.snika.diorasi.dto.WebsiteDTO;
import gr.snika.diorasi.entities.AppUserDetails;
import gr.snika.diorasi.services.WebsiteService;

@RestController
@RequestMapping(path = "/api/websites")
public class WebsiteController {

	  private static final Logger logger = LoggerFactory.getLogger(WebsiteController.class);
	  
	@Autowired
	WebsiteService websiteService;
	
	
	@PostMapping
	public ResponseEntity<String> newWebsite(@RequestBody WebsiteDTO websiteDto, @AuthenticationPrincipal AppUserDetails user) {
		try {
			if (user.isAdmin()) {
				websiteService.saveWebsite(websiteDto, user.getUsername());
				return new ResponseEntity<String>(HttpStatus.OK);
			} else {
				String message = "The current user does not have the ADMIN role in order to create a website entry.";
				logger.error(message);
				return new ResponseEntity<String>(message, HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<String>("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping
	public ResponseEntity<List<WebsiteDTO>> getAllWebsites(@AuthenticationPrincipal AppUserDetails user) {
		try {
			List<WebsiteDTO> websites = websiteService.getAllWebsites(user.getUsername());
			return new ResponseEntity<List<WebsiteDTO>>(websites,HttpStatus.OK);	
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<List<WebsiteDTO>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
}
