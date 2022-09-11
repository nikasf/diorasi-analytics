package gr.snika.diorasi.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gr.snika.diorasi.dto.EventDTO;
import gr.snika.diorasi.dto.EventsCountDTO;
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

	/** If dates are not specified return events from the last month (per day)
	 * 1 day 						-> per 1 hour
	 * 1 day 	< x < 3 months		-> per day
	 * 3 months < x < 4 years(48m)	-> per 1 month
	 * 4 years	< x < ....  		-> per 1 year
	 * */
	//FIXME Use websiteID instead of domain. 
	public Map<String,Integer> retrieveEventMetrics(String websiteIdString, String dateFrom, String dateTo){
		List<EventsCountDTO> dtos = new ArrayList<>();
		UUID websiteId = UUID.fromString(websiteIdString);
		
		//Retrieve metrics for today
		if (dateFrom == null || dateFrom.isBlank() || dateTo == null || dateTo.isBlank()) {
			LocalDate localDate = LocalDate.now();
			dtos = retrieveEventMetricsForOneDay(websiteId, localDate);
		} // Retrieve events of one specific day
		else if (dateFrom.equals(dateTo)) {
			LocalDate localDate = LocalDate.parse(dateFrom);
			dtos = retrieveEventMetricsForOneDay(websiteId, localDate);
		} // Retrieve events of over a day
		else {
			LocalDate initializedDateFrom = initializeLocalDate(dateFrom);
			LocalDate initializedDateTo = initializeLocalDate(dateTo);
			
			int months = monthsDiff(initializedDateFrom, initializedDateTo);
		    
		    // x <= 3 months (and ~30 days)
		    if (months <= 3) {
		    	dtos = eventRepository.findAllByDay(websiteId, initializedDateFrom, initializedDateTo);
		    } else if (months > 3 && months <= 48) {
		    	dtos = eventRepository.findAllByMonth(websiteId, initializedDateFrom, initializedDateTo);
		    } else if (months > 48) {
		    	dtos = eventRepository.findAllByYear(websiteId, initializedDateFrom, initializedDateTo);
		    }
		}
		
	    Map<String, Integer> map = dtos.stream()
	    								.collect(Collectors.toMap(EventsCountDTO::getTimefield, EventsCountDTO::getNumberofevents));
	    
		return map;
	}

	public int monthsDiff(LocalDate initializedDateFrom, LocalDate initializedDateTo) {
		Period period = Period.between(initializedDateFrom, initializedDateTo);
		int years = period.getYears();
		int months = period.getMonths() + 12*years;
		return months;
	}

	private List<EventsCountDTO> retrieveEventMetricsForOneDay(UUID websiteId, LocalDate localDate) {
		LocalDateTime startOfDay = LocalTime.MIN.atDate(localDate);
		LocalDateTime endOfDay = LocalTime.MAX.atDate(localDate).minusSeconds(1);
		
		return eventRepository.findAllByHour(websiteId, startOfDay, endOfDay);
	}

	private LocalDate initializeLocalDate(String stringDate) {
		return LocalDate.parse(stringDate);
	}

	public Event convertToEvent(EventDTO dto) {
		Event event = new Event();
		event.setEventType(dto.getEventType());
		event.setEventValue(dto.getEventValue());
		event.setUrl(dto.getUrl());
		return event;
	}
	
	//FIXME Delete me if not used
	public EventDTO convertToEventDTO(Event event) {
		EventDTO eventDTO = new EventDTO();
		eventDTO.setCreatedAt(event.getCreatedAt());
		eventDTO.setEventType(event.getEventType());
		eventDTO.setEventValue(event.getEventValue());
		eventDTO.setDomain(event.getWebsite().getDomain());
		eventDTO.setUrl(event.getUrl());
		return eventDTO;
	}

}
