package gr.snika.diorasi.controllers;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gr.snika.diorasi.dto.AppUserDTO;
import gr.snika.diorasi.services.UserService;

@RestController
@RequestMapping(path = "${apiPrefix}/users")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PostMapping
	public ResponseEntity<String> newUser(@RequestBody AppUserDTO user) {
		return new ResponseEntity<String>("success",HttpStatus.OK);		
	}
	
	@GetMapping
	String getString() {
		return "Hello, World";
	}

}
