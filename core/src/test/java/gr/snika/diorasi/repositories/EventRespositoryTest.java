package gr.snika.diorasi.repositories;


import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import gr.snika.diorasi.TestUtility;
import gr.snika.diorasi.entities.Event;

@ExtendWith(SpringExtension.class)
@DataJpaTest
//In order to use the actual database use the annotation below with the following value
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@AutoConfigureTestDatabase
public class EventRespositoryTest {
	
	@Autowired private EventRepository eventRepository;
	
	static TestUtility testUtility;
	  
	@Test
	void injectedComponentsAreNotNull(){
	  assertThat(eventRepository).isNotNull();
	}
	
	@Test
	void pageviewEventSavedSuccessfully() {
		Iterable<Event> events = eventRepository.findAll();
	    assertThat(events).extracting(Event::getEventType).contains("pageview", "quotationBrand", "quotationSubmit");
	}

	@Test
	void getAllEventsOfAWebsiteForThelastMonth() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS Z");
		Date from = null, to = null;
		try {
			from = sdf.parse("2022-07-23 00:07:47.992 +0300");
			to = sdf.parse("2022-08-23 00:07:47.992 +0300");
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
	
		Iterable<Event> events = eventRepository.findAllByWebsiteAndDateRange(testUtility.getWebsite(), from , to);
		assertThat(events).hasSize(2);
	}
	
	@BeforeAll
    public static void db_setup(@Autowired UserRepository userRepository, @Autowired WebsiteRepository websiteRepository, @Autowired EventRepository eventRepository) {
        System.out.println("startup - creating DB data");
        testUtility = new TestUtility(userRepository, websiteRepository, eventRepository);
		testUtility.createEventsForRangeSelection();
		System.out.println("Finished - creating DB data");
    }
}
