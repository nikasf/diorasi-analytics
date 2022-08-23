package gr.snika.diorasi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import gr.snika.diorasi.entities.AppUser;
import gr.snika.diorasi.entities.Event;
import gr.snika.diorasi.entities.Website;
import gr.snika.diorasi.enums.Role;
import gr.snika.diorasi.repositories.EventRepository;
import gr.snika.diorasi.repositories.UserRepository;
import gr.snika.diorasi.repositories.WebsiteRepository;

public class TestUtility {

	private UserRepository userRepository;
	private WebsiteRepository websiteRepository;
	private EventRepository eventRepository;
	
	public TestUtility(UserRepository userRepository, WebsiteRepository websiteRepository,
			EventRepository eventRepository) {
		this.userRepository = userRepository;
		this.websiteRepository = websiteRepository;
		this.eventRepository = eventRepository;
	}

	AppUser appUser;
	Website website;
	
	public void createUser() {
		appUser = new AppUser("testUser", "testUser", "testUser@diorasi.gr", true, Role.ADMIN.name());
		userRepository.save(appUser);
	}
	
	public void createWebsite() {
		createUser();
		website = new Website("www.agathi.gr", "Agathi website", new Date(), appUser);
		websiteRepository.save(website);
	}
	
	public void createEventsForRangeSelection() {
		createWebsite();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS Z");
		Event   event1 = null, event2 = null,
				event3 = null, event4 = null,
				event5 = null, event6 = null,
				event7 = null, event8 = null, 
				event9 = null, event10 = null;
	
		try {
			event1 = new Event("sessionId1", sdf.parse("2020-08-10 00:07:47.992 +0300"), "www.agathi.gr/homepage", "pageview", null, "www.google.com");
			event2 = new Event("sessionId2", sdf.parse("2020-08-10 00:10:47.992 +0300"), "www.agathi.gr/homepage", "pageview", null, "www.google.com");
			event3 = new Event("sessionId3", sdf.parse("2020-08-10 00:01:47.992 +0300"), "www.agathi.gr/motor", "quotationBrand", "Audi", "www.google.com");
			event4 = new Event("sessionId4", sdf.parse("2020-08-11 00:07:47.992 +0300"), "www.agathi.gr/motor", "quotationBrand", "BMW", "www.google.com");
			event5 = new Event("sessionId5", sdf.parse("2020-08-12 00:07:47.992 +0300"), "www.agathi.gr/motor", "quotationBrand", "Mercedes", "www.google.com");
			event6 = new Event("sessionId6", sdf.parse("2020-12-23 00:07:47.992 +0300"), "www.agathi.gr/motor", "quotationBrand", "Audi", "www.google.com");
			event7 = new Event("sessionId7", sdf.parse("2021-08-10 00:07:47.992 +0300"), "www.agathi.gr/motor", "quotationSubmit", "Υπολογισμός", "www.google.com");
			event8 = new Event("sessionId8", sdf.parse("2021-08-12 00:07:47.992 +0300"), "www.agathi.gr/house", "pageview", null, "www.google.com");
			event9 = new Event("sessionId9", sdf.parse("2022-08-10 00:07:47.992 +0300"), "www.agathi.gr/house", "pageview", null, "www.google.com");
			event10 = new Event("sessionId10", sdf.parse("2022-08-11 00:07:47.992 +0300"), "www.agathi.gr/house", "pageview", null, "www.google.com");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<Event> events = Arrays.asList(event1,event2,event3, event4,event5,event6,event7,event8,event9,event10);
		
		events.forEach((event) -> {
			event.setWebsite(website);
			eventRepository.save(event);
		});
	}
	
	public Website getWebsite() {
		return websiteRepository.findAll().iterator().next();
	}
	
}
