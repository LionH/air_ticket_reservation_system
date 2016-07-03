package org.tesolin.crossover.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.tesolin.crossover.entity.FlightTicket;
import org.tesolin.crossover.entity.User;

public interface FlightTicketDao extends CrudRepository<FlightTicket,Long> {
	FlightTicket getById(long id);
	List<FlightTicket> findByUser(User user);
}
