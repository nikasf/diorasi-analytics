package gr.snika.diorasi.services;

import static java.time.temporal.ChronoUnit.DAYS;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private static final Logger logger = LoggerFactory.getLogger(EventService.class);
	
	public void saveEvent(EventDTO eventDto) {
		Event event = convertToEvent(eventDto);
		Website website = getWebsite(eventDto.getDomain());
		event.setWebsite(website);
		eventRepository.save(event);
	}
	
	private Website getWebsite(String domain) {
		return websiteRepository.findByDomainName(domain);
	}

	/** If dates are not specified return events from the last month (per day)
	 * 1 day 						-> per 1 hour
	 * 1 day 	< x < 3 months		-> per day
	 * 3 months < x < 6 months		-> per 1 week
	 * 6 months < x < 3 years		-> per 1 month
	 * 3 years	< x < ....  		-> per 1 year
	 * 
	 * */
	public List<EventDTO> getAllEvents(String domain, String dateFrom, String dateTo){
		List<EventDTO> dtos = new ArrayList<>();
		Website website = getWebsite(domain);
		Iterable<Event> events = null;
		Date initializedDateFrom = initializeDate(dateFrom);
		Date initializedDateTo = initializeDate(dateTo);
		
		Date dFrom = initializedDateFrom == null ? oneMonthAgo(): initializedDateFrom;
		Date dTo = initializedDateTo == null ? new Date() : initializedDateFrom;
		
		events = eventRepository.findAllByWebsiteAndDateRange(website, dFrom, dTo);
		events.forEach(event -> dtos.add(convertToEventDTO(event)));
		
		return dtos;
	}

	private Date initializeDate(String stringDate) {
		if (stringDate == null || stringDate.isBlank()) {
			return null;
		}
		
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//		return LocalDate.parse(stringDate, formatter);
		
		Date returnedDate = null;
		try {
			returnedDate = new SimpleDateFormat("dd/MM/yyyy").parse(stringDate);
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}  
		
		return returnedDate;
	}
	
	private Date oneMonthAgo() {
		var today = new Date();
		Calendar c = Calendar.getInstance(); 
		c.setTime(today); 
		c.add(Calendar.MONTH, -1);
		
		return c.getTime();
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
