package gr.snika.diorasi.repositories;


import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
	
	@Autowired private SessionRepository sessionRepository;
	
	static TestUtility testUtility;
	
	@BeforeAll
    public static void db_setup(@Autowired UserRepository userRepository, @Autowired WebsiteRepository websiteRepository, 
    		@Autowired EventRepository eventRepository, @Autowired SessionRepository sessionRepository) {
		System.out.println("startup - creating DB data");
        testUtility = new TestUtility(userRepository, websiteRepository, eventRepository, sessionRepository);
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
	 * 					....
	 * 					16  : 1
	 * 					....
	 * 					23  : 2
	 * 					.....
	 */
	@Test
	void getAllEventsOfAWebsiteByHour() {
		String date = "2022-08-11";
		LocalDate localDate = LocalDate.parse(date);
		LocalDateTime startOfDay = LocalTime.MIN.atDate(localDate);
		LocalDateTime endOfDay = LocalTime.MAX.atDate(localDate).minusSeconds(1);
		
		System.out.println(startOfDay + " " + endOfDay);
	
		List<EventsCountDTO> events = eventRepository.findAllByHour(testUtility.getWebsite().getId(), startOfDay , endOfDay);
		events.forEach(event -> System.out.println(event.getFilterfield()));
		assertThat(events).hasSize(24);
	}
	
	/**
	 * Expected Result: 
	 * 					2020-08-10  : 3
	 * 					2020-08-11  : 1
	 * 					2020-08-12  : 1
	 * 					......
	 * 					2020-09-12	: 0
	 * 					2020-09-13	: 0
	 * 					2020-09-14  : 0
	 */
	@Test
	void getAllEventsOfAWebsiteByDay() {
		String dateFrom = "2020-08-01";
		String dateTo = "2020-09-14";
		LocalDate from = LocalDate.parse(dateFrom);
		LocalDate to = LocalDate.parse(dateTo);
	
		List<EventsCountDTO> events = eventRepository.findAllByDay(testUtility.getWebsite().getId(), from , to);
		assertThat(events).hasSize(45);
	}
	

	/**
	 * Expected Result: 
	 * 					....
	 * 					2020-8   : 5
	 * 					.....
	 * 					2020-12  : 1
	 * 					...
	 * 					2021-08	 : 2
	 * 					....
	 * 					2022-08	 : 4
	 */
	@Test
	void getAllEventsOfAWebsiteByMonth() {
		String dateFrom = "2020-07-31";
		String dateTo = "2022-12-31";
		LocalDate from = LocalDate.parse(dateFrom);
		LocalDate to = LocalDate.parse(dateTo);
	
		List<EventsCountDTO> events = eventRepository.findAllByMonth(testUtility.getWebsite().getId(), from , to);
		assertThat(events).hasSize(30);
	}
	

	/**
	 * Expected Result: 
	 * 					 2019  : 0
	 *				     2020  : 6
	 *					 2021  : 2
	 *					 2022  : 4
	 *					 2023  : 0
	 */
	@Test
	void getAllEventsOfAWebsiteByYear() {
		String dateFrom = "2019-01-01";
		String dateTo = "2023-12-31";
		LocalDate from = LocalDate.parse(dateFrom);
		LocalDate to = LocalDate.parse(dateTo);
	
		List<EventsCountDTO> events = eventRepository.findAllByYear(testUtility.getWebsite().getId(), from , to);
		assertThat(events).hasSize(5);
	}
	
	
	@Test
	void getAllEventsOfAWebsitePerPage() {
		String dateFrom = "2020-07-31";
		String dateTo = "2022-12-31";
		LocalDate from = LocalDate.parse(dateFrom);
		LocalDate to = LocalDate.parse(dateTo);
	
		List<EventsCountDTO> events = eventRepository.findPagesCount(testUtility.getWebsite().getId(), from , to);
		assert(events.get(0).getFilterfield()).equals("www.agathi.gr/house");
		assert(events.get(0).getNumberofevents()).equals(3);
		assertThat(events).hasSize(2);
	}
	
	@Test
	void getAllEventsOfAWebsitePerBrowser() {
		String dateFrom = "2022-08-01";
		String dateTo = "2022-10-01";
		LocalDateTime from = LocalTime.MIN.atDate(LocalDate.parse(dateFrom));
		LocalDateTime to = LocalTime.MAX.atDate(LocalDate.parse(dateTo)).minusSeconds(1);
	
		List<EventsCountDTO> events = sessionRepository.findNoOfEventsPerBrowser(testUtility.getWebsite().getId(), from , to);
		assert(events.get(0).getFilterfield()).equals("Mozilla Firefox");
		assert(events.get(0).getNumberofevents()).equals(2);
		assertThat(events).hasSize(2);
	}
	
	@Test
	void getAllEventsOfAWebsitePerDevice() {
		String dateFrom = "2022-08-01";
		String dateTo = "2022-10-01";
		LocalDateTime from = LocalTime.MIN.atDate(LocalDate.parse(dateFrom));
		LocalDateTime to = LocalTime.MAX.atDate(LocalDate.parse(dateTo)).minusSeconds(1);
	
		List<EventsCountDTO> events = sessionRepository.findNoOfEventsPerDevice(testUtility.getWebsite().getId(), from , to);
		assert(events.get(0).getFilterfield()).equals("Tablet");
		assert(events.get(0).getNumberofevents()).equals(2);
		assertThat(events).hasSize(2);
	}
	
	@Test
	void getAllEventsOfAWebsitePerOS() {
		String dateFrom = "2022-08-01";
		String dateTo = "2022-10-01";
		LocalDateTime from = LocalTime.MIN.atDate(LocalDate.parse(dateFrom));
		LocalDateTime to = LocalTime.MAX.atDate(LocalDate.parse(dateTo)).minusSeconds(1);
	
		List<EventsCountDTO> events = sessionRepository.findNoOfEventsPerOS(testUtility.getWebsite().getId(), from , to);
		assert(events.get(0).getFilterfield()).equals("MAc OS");
		assert(events.get(0).getNumberofevents()).equals(2);
		assertThat(events).hasSize(2);
	}
	
	@Test
	void getAllEventsOfAWebsitePerCountry() {
		String dateFrom = "2022-08-01";
		String dateTo = "2022-10-01";
		LocalDateTime from = LocalTime.MIN.atDate(LocalDate.parse(dateFrom));
		LocalDateTime to = LocalTime.MAX.atDate(LocalDate.parse(dateTo)).minusSeconds(1);
	
		List<EventsCountDTO> events = sessionRepository.findNoOfEventsPerCountry(testUtility.getWebsite().getId(), from , to);
		assert(events.get(0).getFilterfield()).equals("GR");
		assert(events.get(0).getNumberofevents()).equals(2);
		assertThat(events).hasSize(2);
	}
	
	@Test
	void getAllEventsOfAWebsitePerResolution() {
		String dateFrom = "2022-08-01";
		String dateTo = "2022-10-01";
		LocalDateTime from = LocalTime.MIN.atDate(LocalDate.parse(dateFrom));
		LocalDateTime to = LocalTime.MAX.atDate(LocalDate.parse(dateTo)).minusSeconds(1);
	
		List<EventsCountDTO> events = sessionRepository.findNoOfEventsPerResolution(testUtility.getWebsite().getId(), from , to);
		events.forEach(e -> {
			System.out.println(e.getNumberofevents());
			System.out.println(e.getFilterfield());
		});
		assert(events.get(0).getFilterfield()).equals("1366x768");
		assert(events.get(0).getNumberofevents()).equals(2);
		assertThat(events).hasSize(2);
	}
	

}
