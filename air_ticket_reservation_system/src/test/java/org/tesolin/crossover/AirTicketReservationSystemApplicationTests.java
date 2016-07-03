package org.tesolin.crossover;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.tesolin.crossover.entity.Flight;
import org.tesolin.crossover.entity.FlightTicket;
import org.tesolin.crossover.service.FlightService;
import org.tesolin.crossover.service.TicketService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AirTicketReservationSystemApplication.class)
@WebAppConfiguration
public class AirTicketReservationSystemApplicationTests {

	@Autowired
	private FlightService flightService;
	
	@Autowired
	private TicketService ticketService;
	
	private final Date dateFrom = DateUtils.round(DateUtils.addDays(new Date(), 1),Calendar.DATE);
	private final Date dateTo = DateUtils.round(DateUtils.addDays(new Date(), 2),Calendar.DATE);
	
	@Test
	public void testSearchFlight() {
		Map<String, Collection<Flight>> searchFlights = flightService.searchFlights(1, 2, dateFrom, dateTo);
		Assert.assertFalse("Search Flights", searchFlights.isEmpty());
	}
	
	@Test
	public void testBookFlight() {
		Map<String, Collection<Flight>> searchFlights = flightService.searchFlights(1, 2, dateFrom, dateTo);
		
		Flight flight = searchFlights.get("oneway").iterator().next();
		
		SecurityContextHolder.getContext().setAuthentication(new TestingAuthenticationToken("user","credential"));
		
		Collection<FlightTicket> bookFlight = ticketService.bookFlight(flight.getId(), null, 1, 0, 0);
		Assert.assertFalse("Search Flights", bookFlight.isEmpty());
	}

}
