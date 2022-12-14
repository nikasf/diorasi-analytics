package gr.snika.diorasi.controllers;

import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gr.snika.diorasi.dto.EventDTO;
import gr.snika.diorasi.dto.EventsDTOCountList;
import gr.snika.diorasi.entities.AppUserDetails;
import gr.snika.diorasi.services.EventService;

@RestController
@RequestMapping(path = "/api/websites")
public class EventController {

	  private static final Logger logger = LoggerFactory.getLogger(EventController.class);
	  
	@Autowired
	EventService eventService;
	
	@PostMapping(path="/{websiteId}")
	public ResponseEntity<String> newEvent(@PathVariable String websiteId, @RequestBody EventDTO eventDto, @AuthenticationPrincipal AppUserDetails user) {
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
	
	@GetMapping(path="/{websiteId}/events")
	public ResponseEntity<EventsDTOCountList> getEvents(@PathVariable String websiteId, @RequestParam Optional<String> dateFrom, @RequestParam Optional<String> dateTo, @AuthenticationPrincipal AppUserDetails user) {
		try {
			if (user.isAdmin()) {
				Map<String, Integer> eventCounts = eventService.retrieveEventMetrics(websiteId, dateFrom.orElse(null), dateTo.orElse(null));
				EventsDTOCountList list = new EventsDTOCountList(eventCounts);
				return new ResponseEntity<EventsDTOCountList>(list, HttpStatus.OK);	
			} else {
				String message = "The current user does not have the ADMIN role in order to retrieve events.";
				logger.error(message);
				return new ResponseEntity<EventsDTOCountList>(HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<EventsDTOCountList>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(path="/{websiteId}/events/{filter}")
	public ResponseEntity<EventsDTOCountList> getEventsWithFilter(@PathVariable String websiteId, @PathVariable String filter, @RequestParam Optional<String> dateFrom, @RequestParam Optional<String> dateTo, @AuthenticationPrincipal AppUserDetails user) {
		try {
			if (user.isAdmin()) {
				Map<String, Integer> eventCounts = eventService.retrieveEventMetricsWithFilter(websiteId, filter, dateFrom.orElse(null), dateTo.orElse(null));
				EventsDTOCountList list = new EventsDTOCountList(eventCounts);
				return new ResponseEntity<EventsDTOCountList>(list, HttpStatus.OK);	
			} else {
				String message = "The current user does not have the ADMIN role in order to retrieve events.";
				logger.error(message);
				return new ResponseEntity<EventsDTOCountList>(HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<EventsDTOCountList>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
