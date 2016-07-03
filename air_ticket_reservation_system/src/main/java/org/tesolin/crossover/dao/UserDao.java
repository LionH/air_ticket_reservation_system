package org.tesolin.crossover.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.tesolin.crossover.entity.Flight;
import org.tesolin.crossover.entity.User;

public interface UserDao extends CrudRepository<User,Long> {
	User getById(long id);
	User getByLogin(String login);
}
