package org.tesolin.crossover.initialize;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.IntStream;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tesolin.crossover.dao.FlightDao;
import org.tesolin.crossover.dao.UserDao;
import org.tesolin.crossover.entity.Flight;
import org.tesolin.crossover.entity.Role;
import org.tesolin.crossover.entity.User;

@Component
public class Init {

	@Autowired
	private FlightDao flightDao;
	
	@Autowired
	private UserDao userDao;

	@PostConstruct
	@Transactional
	private void init() {
		IntStream.range(0, 20).forEach(i -> {
			Flight flight = new Flight();
			flight.setDate(DateUtils.round(DateUtils.addDays(new Date(), i/2),Calendar.DATE));
			flight.setOrigin(1);
			flight.setDestination(2);
			flight.setPrice(BigDecimal.valueOf(Math.random()*i*10));
			flight.setAvailableSeats(i);
			flight.setPriceFactor(BigDecimal.ONE);
			flightDao.save(flight);
		});

		IntStream.range(0, 20).forEach(i -> {
			Flight flight = new Flight();
			flight.setDate(DateUtils.round(DateUtils.addDays(new Date(), i/2),Calendar.DATE));
			flight.setOrigin(2);
			flight.setDestination(1);
			flight.setPrice(BigDecimal.valueOf(Math.random()*i*10));
			flight.setAvailableSeats(i);
			flight.setPriceFactor(BigDecimal.ONE);
			flightDao.save(flight);
		});

		IntStream.range(0, 20).forEach(i -> {
			Flight flight = new Flight();
			flight.setDate(DateUtils.round(DateUtils.addDays(new Date(), i/2),Calendar.DATE));
			flight.setOrigin(3);
			flight.setDestination(1);
			flight.setPrice(BigDecimal.valueOf(Math.random()*i*10));
			flight.setAvailableSeats(i);
			flight.setPriceFactor(BigDecimal.ONE);
			flightDao.save(flight);
		});

		IntStream.range(0, 20).forEach(i -> {
			Flight flight = new Flight();
			flight.setDate(DateUtils.round(DateUtils.addDays(new Date(), i/2),Calendar.DATE));
			flight.setOrigin(1);
			flight.setDestination(3);
			flight.setPrice(BigDecimal.valueOf(Math.random()*i*10));
			flight.setAvailableSeats(i);
			flight.setPriceFactor(BigDecimal.ONE);
			flightDao.save(flight);
		});

		IntStream.range(0, 20).forEach(i -> {
			Flight flight = new Flight();
			flight.setDate(DateUtils.round(DateUtils.addDays(new Date(), i/2),Calendar.DATE));
			flight.setOrigin(2);
			flight.setDestination(3);
			flight.setPrice(BigDecimal.valueOf(Math.random()*i*10));
			flight.setAvailableSeats(i);
			flight.setPriceFactor(BigDecimal.ONE);
			flightDao.save(flight);
		});
		
		IntStream.range(0, 20).forEach(i -> {
			Flight flight = new Flight();
			flight.setDate(DateUtils.round(DateUtils.addDays(new Date(), i/2),Calendar.DATE));
			flight.setOrigin(3);
			flight.setDestination(2);
			flight.setPrice(BigDecimal.valueOf(Math.random()*i*10));
			flight.setAvailableSeats(i);
			flight.setPriceFactor(BigDecimal.ONE);
			flightDao.save(flight);
		});
		
		User user = new User();
		user.setLogin("admin");
		user.setPassword("password");
		user.setFirstName("Lionel");
		user.setLastName("Tesolin");
		user.setRole(Role.ADMIN);
		userDao.save(user);
		
		user = new User();
		user.setLogin("login");
		user.setPassword("password");
		user.setFirstName("Peter");
		user.setLastName("Gabriel");
		user.setRole(Role.USER);
		userDao.save(user);
	}
}
