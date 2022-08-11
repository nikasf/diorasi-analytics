package gr.snika.diorasi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gr.snika.diorasi.entities.AppUser;
import gr.snika.diorasi.repositories.UserRepository;

@RestController
@RequestMapping(path = "${apiPrefix}/users")
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	@PostMapping
	AppUser newUser(@RequestBody AppUser newUser) {
		return userRepository.save(newUser);		
	}
	
	@GetMapping
	String getString() {
		return "Hello, World";
	}

}
