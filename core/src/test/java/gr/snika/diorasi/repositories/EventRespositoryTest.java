package gr.snika.diorasi.repositories;


import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import gr.snika.diorasi.TestUtility;
import gr.snika.diorasi.dto.EventsCountDTO;
import gr.snika.diorasi.entities.Event;

@SpringBootTest
@ActiveProfiles("test")
public class EventRespositoryTest {
	
	@Autowired private EventRepository eventRepository;
	
	static TestUtility testUtility;
	
	@BeforeAll
    public static void db_setup(@Autowired UserRepository userRepository, @Autowired WebsiteRepository websiteRepository, @Autowired EventRepository eventRepository) {
		System.out.println("startup - creating DB data");
        testUtility = new TestUtility(userRepository, websiteRepository, eventRepository);
		testUtility.createEventsForRangeSelection();
		System.out.println("Finished - creating DB data");
    }
	  
	@Test
	void injectedComponentsAreNotNull(){
	  assertThat(eventRepository).isNotNull();
	}
	
	@Test
	void validateAllInsertedTypesOfEvents() {
		Iterable<Event> events = eventRepository.findAll();
	    assertThat(events).extracting(Event::getEventType).contains("pageview", "quotationBrand", "quotationSubmit");
	}

	/**
	 * Expected Result: 
	 * 					21  : 1
	 * 					16  : 1
	 * 					15  : 1
	 */
	@Test
	void getAllEventsOfAWebsiteByHour() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS Z");
		Date from = null, to = null;
		try {
			from = sdf.parse("2020-08-10 00:00:00.000 +0300");
			to = sdf.parse("2020-08-10 23:59:59.999 +0300");
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
	
		List<EventsCountDTO> events = eventRepository.findAllByHour(testUtility.getWebsite().getId(), from , to);
		assertThat(events).hasSize(3);
	}
	
	/**
	 * Expected Result: 
	 * 					2020-12-23  : 1
	 * 					2020-08-12  : 1
	 * 					2020-08-11  : 1
	 * 					2020-08-10  : 3
	 */
	@Test
	void getAllEventsOfAWebsiteByDay() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS Z");
		Date from = null, to = null;
		try {
			from = sdf.parse("2020-08-01 15:07:47.992 +0300");
			to = sdf.parse("2020-12-24 00:07:47.992 +0300");
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
	
		List<EventsCountDTO> events = eventRepository.findAllByDay(testUtility.getWebsite().getId(), from , to);
		assertThat(events).hasSize(4);
	}
	
	/**
	 * Expected Result: 
	 * 					 2022  : 4
	 *				     2021  : 2
	 *					 2020  : 6
	 */
	@Test
	void getAllEventsOfAWebsiteByMonth() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS Z");
		Date from = null, to = null;
		try {
			from = sdf.parse("2020-12-01 00:07:47.992 +0300");
			to = sdf.parse("2021-12-31 00:07:47.992 +0300");
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
	
		List<EventsCountDTO> events = eventRepository.findAllByMonth(testUtility.getWebsite().getId(), from , to);
		assertThat(events).hasSize(2);
	}
	
	/**
	 * Expected Result: 
	 * 					2021-8   : 2
	 * 					2020-12  : 1
	 */
	@Test
	void getAllEventsOfAWebsiteByYear() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS Z");
		Date from = null, to = null;
		try {
			from = sdf.parse("2020-04-18 00:00:00.000 +0300");
			to = sdf.parse("2022-08-18 23:59:59.999 +0300");
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
	
		List<EventsCountDTO> events = eventRepository.findAllByYear(testUtility.getWebsite().getId(), from , to);
		assertThat(events).hasSize(3);
	}
	
}
