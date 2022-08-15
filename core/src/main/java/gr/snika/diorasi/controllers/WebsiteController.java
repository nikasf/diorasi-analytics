package gr.snika.diorasi.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gr.snika.diorasi.dto.WebsiteDTO;
import gr.snika.diorasi.services.WebsiteService;

@RestController
@RequestMapping(path = "/api/websites")
public class WebsiteController {

	  private static final Logger logger = LoggerFactory.getLogger(WebsiteController.class);
	  
	@Autowired
	WebsiteService websiteService;
	
	
	@PostMapping
	public ResponseEntity<String> newWebsite(@RequestBody WebsiteDTO websiteDto) {
		try {
			websiteService.saveWebsite(websiteDto);
			return new ResponseEntity<String>(HttpStatus.OK);	
		} catch (Exception e) {
			logger.info(e.getMessage());
			return new ResponseEntity<String>("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
