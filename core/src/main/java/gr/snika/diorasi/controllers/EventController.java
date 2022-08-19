package gr.snika.diorasi.controllers;

import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gr.snika.diorasi.dto.EventDTO;
import gr.snika.diorasi.entities.AppUserDetails;
import gr.snika.diorasi.services.EventService;

@RestController
@RequestMapping(path = "/api/events")
public class EventController {

	  private static final Logger logger = LoggerFactory.getLogger(EventController.class);
	  
	@Autowired
	EventService eventService;
	
	@PostMapping
	public ResponseEntity<String> newEvent(@RequestBody EventDTO eventDto, @AuthenticationPrincipal AppUserDetails user) {
		try {
			if (user.isAdmin()) {
				eventService.saveEvent(eventDto);
				return new ResponseEntity<String>(HttpStatus.OK);
			} else {
				String message = "The current user does not have the ADMIN role in order to create a event entry.";
				logger.error(message);
				return new ResponseEntity<String>(message, HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<String>("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping
	public ResponseEntity<List<EventDTO>> getEvents(@RequestParam String domain, @RequestParam Optional<String> dateFrom, @RequestParam Optional<String> dateTo, @AuthenticationPrincipal AppUserDetails user) {
		try {
			if (user.isAdmin()) {
				List<EventDTO> websites = eventService.getAllEvents(domain, dateFrom.orElse(null), dateTo.orElse(null));
				return new ResponseEntity<List<EventDTO>>(websites,HttpStatus.OK);	
			} else {
				String message = "The current user does not have the ADMIN role in order to create a event entry.";
				logger.error(message);
				return new ResponseEntity<List<EventDTO>>(HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<List<EventDTO>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
