package gr.snika.diorasi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gr.snika.diorasi.dto.EventDTO;
import gr.snika.diorasi.entities.Event;
import gr.snika.diorasi.entities.Website;
import gr.snika.diorasi.repositories.EventRepository;
import gr.snika.diorasi.repositories.WebsiteRepository;

@Service
public class EventService {
	
	@Autowired
	EventRepository eventRepository;

	@Autowired
	WebsiteRepository websiteRepository;
	
	
	public void saveEvent(EventDTO eventDto) {
		Event event = convertToEvent(eventDto);
		Website website = getWebsite(eventDto.getDomain());
		event.setWebsite(website);
		eventRepository.save(event);
	}
	
	private Website getWebsite(String domain) {
		return websiteRepository.findByDomainName(domain);
	}

	public List<EventDTO> getAllEvents(String domain){
		List<EventDTO> dtos = new ArrayList<>();
		Website website = getWebsite(domain);
		Iterable<Event> events = eventRepository.findAllByWebsite(website);
		events.forEach(event -> dtos.add(convertToEventDTO(event)));
		
		return dtos;
	}
	
	
	public Event convertToEvent(EventDTO dto) {
		Event event = new Event();
		event.setEventType(dto.getEventType());
		event.setEventValue(dto.getEventValue());
		event.setUrl(dto.getUrl());
		event.setSessionId(dto.getSessionId());
		return event;
	}
	
	public EventDTO convertToEventDTO(Event event) {
		EventDTO eventDTO = new EventDTO();
		eventDTO.setCreatedAt(event.getCreatedAt());
		eventDTO.setEventType(event.getEventType());
		eventDTO.setEventValue(event.getEventValue());
		eventDTO.setSessionId(event.getSessionId());
		eventDTO.setDomain(event.getWebsite().getDomain());
		eventDTO.setUrl(event.getUrl());
		return eventDTO;
	}

}
