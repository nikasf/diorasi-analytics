package gr.snika.diorasi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import gr.snika.diorasi.entities.Users;
import gr.snika.diorasi.repositories.UsersRepository;

@RestController
public class UsersController {
	
	@Autowired
	UsersRepository usersRespository;
	
	
	@PostMapping("/users")
	Users newUser(@RequestBody Users newUser) {
	  return usersRespository.save(newUser);
	}

}
