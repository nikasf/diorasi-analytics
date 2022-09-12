package gr.snika.diorasi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import gr.snika.diorasi.repositories.EventRepository;
import gr.snika.diorasi.repositories.SessionRepository;
import gr.snika.diorasi.repositories.WebsiteRepository;
import gr.snika.diorasi.services.EventService;

@SpringBootTest
@ActiveProfiles("test")
public class EventServiceTest {

	@Mock
	private EventRepository eventRepository;
	
	@Mock
	private WebsiteRepository websiteRepository;
	
	@Mock
	private SessionRepository sessionRepository;
	
	@InjectMocks
	private EventService eventService = new EventService();
	
	
	@BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
	
	@DisplayName("Given two different dates 4 months apart, should return 4")
	@Test
	void return3MonthsAsDiff() {
		String dateFrom = "2017-05-14";
		String dateTo = "2022-09-11";
		LocalDate from = LocalDate.parse(dateFrom);
		LocalDate to = LocalDate.parse(dateTo);
		
		int actual = eventService.monthsDiff(from, to);
		assertEquals(actual, 63);
	}
	
	//TODO: Create a parameterized test for all the different dates and methods that must be called
	@ParameterizedTest
	@MethodSource("provideParameters")
	void callPerHourResults(String dateFrom, String dateTo) { 
		UUID websiteId = UUID.randomUUID();
		eventService.retrieveEventMetrics(websiteId.toString(), dateFrom, dateTo);
		
		verify(this.eventRepository).findAllByHour(any(UUID.class), any(LocalDateTime.class), any(LocalDateTime.class));
	}
	
	private static Stream<Arguments> provideParameters() {
	    return Stream.of(
	            Arguments.of(null, null),
	            Arguments.of("2021-10-14", "2021-10-14")
	    );
	}
	
	@Test
	void callPerDayResults() { //3 months and 27 days
		String dateFrom = "2022-05-14";
		String dateTo = "2022-09-11";
		LocalDate from = LocalDate.parse(dateFrom);
		LocalDate to = LocalDate.parse(dateTo);
		UUID websiteId = UUID.randomUUID();
		eventService.retrieveEventMetrics(websiteId.toString(), dateFrom, dateTo);
		
		verify(this.eventRepository).findAllByDay(websiteId, from, to); 
	}
	
	@Test
	void callPerMonthResults() { 
		String dateFrom = "2020-05-14";
		String dateTo = "2022-09-11";
		LocalDate from = LocalDate.parse(dateFrom);
		LocalDate to = LocalDate.parse(dateTo);
		UUID websiteId = UUID.randomUUID();
		eventService.retrieveEventMetrics(websiteId.toString(), dateFrom, dateTo);
		
		verify(this.eventRepository).findAllByMonth(websiteId, from, to); 
	}
	
	@Test
	void callPerYearResults() { 
		String dateFrom = "2017-05-14";
		String dateTo = "2022-09-11";
		LocalDate from = LocalDate.parse(dateFrom);
		LocalDate to = LocalDate.parse(dateTo);
		UUID websiteId = UUID.randomUUID();
		eventService.retrieveEventMetrics(websiteId.toString(), dateFrom, dateTo);
		
		verify(this.eventRepository).findAllByYear(websiteId, from, to); 
	}
	
	@Test
	void callPerDeviceResults() { 
		String dateFrom = "2017-05-14";
		String dateTo = "2022-09-11";
		LocalDateTime from = LocalTime.MIN.atDate(LocalDate.parse(dateFrom));
		LocalDateTime to = LocalTime.MAX.atDate(LocalDate.parse(dateTo)).minusSeconds(1);
		UUID websiteId = UUID.randomUUID();
		eventService.retrieveEventMetricsWithFilter(websiteId.toString(), "device", dateFrom, dateTo);
		
		verify(this.sessionRepository).findNoOfEventsPerDevice(websiteId, from, to);
	}
	
	@Test
	void callPerOSResults() { 
		String dateFrom = "2017-05-14";
		String dateTo = "2022-09-11";
		LocalDateTime from = LocalTime.MIN.atDate(LocalDate.parse(dateFrom));
		LocalDateTime to = LocalTime.MAX.atDate(LocalDate.parse(dateTo)).minusSeconds(1);
		UUID websiteId = UUID.randomUUID();
		eventService.retrieveEventMetricsWithFilter(websiteId.toString(), "os", dateFrom, dateTo);
		
		verify(this.sessionRepository).findNoOfEventsPerOS(websiteId, from, to);
	}

}
