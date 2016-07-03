package org.tesolin.crossover.service;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginService {

	@RequestMapping("/user")
	public Principal user(Principal user) {
		return user;
	}
}
