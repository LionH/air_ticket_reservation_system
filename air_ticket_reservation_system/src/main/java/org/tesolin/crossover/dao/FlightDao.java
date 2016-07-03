package org.tesolin.crossover.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.tesolin.crossover.entity.Flight;

public interface FlightDao extends CrudRepository<Flight,Long> {
	Flight getById(long id);
	List<Flight> findByOrigin(long origin);
	List<Flight> findByOriginAndDestination(long origin, long destination);
	List<Flight> findByOriginAndDestinationAndDate(long origin, long destination, Date date);
}
