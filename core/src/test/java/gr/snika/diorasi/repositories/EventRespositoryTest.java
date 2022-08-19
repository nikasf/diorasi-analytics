package gr.snika.diorasi.repositories;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import gr.snika.diorasi.entities.AppUser;
import gr.snika.diorasi.entities.Event;
import gr.snika.diorasi.entities.Website;
import gr.snika.diorasi.enums.Role;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
//In order to use the actual database use the annotation below with the following value
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureTestDatabase
public class EventRespositoryTest {
	
	@Autowired private UserRepository userRepository;
	@Autowired private WebsiteRepository websiteRepository;
	@Autowired private EventRepository eventRepository;
	
	AppUser appUser;
	
	Website website;
	  
	@Test
	void injectedComponentsAreNotNull(){
	  assertThat(eventRepository).isNotNull();
	}
	
	@Test
	void pageviewEventSavedSuccessfully() {
		createInitialData();
		Iterable<Event> events = eventRepository.findAll();
	    assertThat(events).extracting(Event::getEventType).containsOnly("pageview");
	}

	private void createInitialData() {
		appUser = new AppUser("testUser", "testUser", "testUser@diorasi.gr", true, Role.ADMIN.name());
		userRepository.save(appUser);
		
		website = new Website("www.agathi.gr", "Agathis website", new Date(), appUser);
		websiteRepository.save(website);
		
		Event event = new Event("sessionId", new Date(), "www.agathi.gr/homepage", "pageview", null, null);
		event.setWebsite(website);
		
		eventRepository.save(event);
		
	}
}
