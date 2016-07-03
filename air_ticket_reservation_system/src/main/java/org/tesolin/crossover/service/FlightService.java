package org.tesolin.crossover.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tesolin.crossover.dao.FlightDao;
import org.tesolin.crossover.entity.Flight;

@RestController
public class FlightService {
	
	@Autowired
	private FlightDao flightDao;

	@RequestMapping("/flights")
	public Collection<Flight> all() {
		Collection<Flight> ret = new ArrayList<Flight>();
		flightDao.findAll().forEach(ret::add);
		return ret;
	}
	
	@RequestMapping("/searchflights")
	public Map<String,Collection<Flight>> searchFlights(long origin, long destination, @DateTimeFormat(pattern="yyyy-MM-dd") Date dateFrom, @DateTimeFormat(pattern="yyyy-MM-dd") Date dateTo) {
		Collection<Flight> oneway = new ArrayList<Flight>();
		Collection<Flight> roundtrip = new ArrayList<Flight>();
		flightDao.findByOriginAndDestinationAndDate(origin, destination, dateFrom).forEach(oneway::add);
		flightDao.findByOriginAndDestinationAndDate(destination, origin, dateTo).forEach(roundtrip::add);
		
		Map<String,Collection<Flight>> ret = new HashMap<String, Collection<Flight>>();
		ret.put("oneway", oneway);
		ret.put("roundtrip", roundtrip);
		return ret;
	}
}
