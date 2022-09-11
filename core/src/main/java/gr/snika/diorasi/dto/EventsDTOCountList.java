package gr.snika.diorasi.dto;

import java.util.Map;

public class EventsDTOCountList {
	
	Map<String, Integer> events;

	public EventsDTOCountList(Map<String, Integer> events) {
		super();
		this.events = events;
	}

	public Map<String, Integer> getEvents() {
		return events;
	}

	public void setEvents(Map<String, Integer> events) {
		this.events = events;
	}
	
	
}
