package gr.snika.diorasi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
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

import gr.snika.diorasi.entities.AppUser;
import gr.snika.diorasi.entities.Website;
import gr.snika.diorasi.repositories.EventRepository;
import gr.snika.diorasi.repositories.WebsiteRepository;
import gr.snika.diorasi.services.EventService;

@SpringBootTest
@ActiveProfiles("test")
public class EventServiceTest {

	@Mock
	private EventRepository eventRepository;
	
	@Mock
	private WebsiteRepository websiteRepository;
	
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
	
	//FIXME: Create a parameterized test for all the different dates and methods that must be called
	@ParameterizedTest
	@MethodSource("provideParameters")
	void callPerHourResults(String dateFrom, String dateTo) { 
		UUID websiteId = UUID.randomUUID();
		when(this.websiteRepository.findByDomainName("domain")).thenReturn(new Website(websiteId, "domain", "test", new Date(), new AppUser()));
		
		eventService.retrieveEventMetrics("domain", dateFrom, dateTo);
		
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
		
		when(this.websiteRepository.findByDomainName("domain")).thenReturn(new Website(websiteId, "domain", "test", new Date(), new AppUser()));
		eventService.retrieveEventMetrics("domain", dateFrom, dateTo);
		
		verify(this.eventRepository).findAllByDay(websiteId, from, to); 
	}
	
	@Test
	void callPerMonthResults() { 
		String dateFrom = "2020-05-14";
		String dateTo = "2022-09-11";
		LocalDate from = LocalDate.parse(dateFrom);
		LocalDate to = LocalDate.parse(dateTo);
		UUID websiteId = UUID.randomUUID();
		
		when(this.websiteRepository.findByDomainName("domain")).thenReturn(new Website(websiteId, "domain", "test", new Date(), new AppUser()));
		eventService.retrieveEventMetrics("domain", dateFrom, dateTo);
		
		verify(this.eventRepository).findAllByMonth(websiteId, from, to); 
	}
	
	@Test
	void callPerYearResults() { 
		String dateFrom = "2017-05-14";
		String dateTo = "2022-09-11";
		LocalDate from = LocalDate.parse(dateFrom);
		LocalDate to = LocalDate.parse(dateTo);
		UUID websiteId = UUID.randomUUID();
		
		when(this.websiteRepository.findByDomainName("domain")).thenReturn(new Website(websiteId, "domain", "test", new Date(), new AppUser()));
		eventService.retrieveEventMetrics("domain", dateFrom, dateTo);
		
		verify(this.eventRepository).findAllByYear(websiteId, from, to); 
	}

}
