package com.example.authenticatingldap;

import org.springframework.security.ldap.userdetails.Person;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.ldap.userdetails.Person;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@RestController
public class HomeController {

	
	@GetMapping("/")
	public String index(Authentication authentication, @AuthenticationPrincipal Person person) {
		// String person1  = person.getSn();
		return "Welcome to the home page , "+authentication.getName()+"! ---";
		// return "Welcome to the home page , "+"! ---";
	}

	@GetMapping("/friendly")
	public String hello(@AuthenticationPrincipal Person person) {
		return "Hello, " + person.getSn() + "!!";
	}
}
