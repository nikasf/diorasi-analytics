package gr.snika.diorasi.controllers;

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

	@Autowired
	WebsiteService websiteService;
	
	
	@PostMapping
	public ResponseEntity<String> newWebsite(@RequestBody WebsiteDTO websiteDto) {
		return new ResponseEntity<String>(HttpStatus.OK);		
	}
	
}
