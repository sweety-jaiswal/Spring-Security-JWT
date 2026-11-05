package com.example.spring_security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.spring_security.model.Person;
import com.example.spring_security.repository.CollegeRepository;

@Service
public class CollegeService {
	
	@Autowired
	private CollegeRepository collegeRepository;
	
	@Autowired
	private JWTService jwtService;
	
	@Autowired
	AuthenticationManager authManager;
	
	PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public String addPerson(Person person) {
		 person.setPassword(passwordEncoder.encode(person.getPassword()));
		 person.setRoles("STUDENT");      //  roles are added at the backend
		 collegeRepository.save(person);
		 return "Person Saved!";
	}
	
	public String addAdmin(Person person) {
		 person.setPassword(passwordEncoder.encode(person.getPassword()));
		 person.setRoles("ADMIN");      //  roles are added at the backend
		 collegeRepository.save(person);
		 return "Admin Saved!";
	}

	public String verify(Person person) {
		
		Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(person.getUsername(), person.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(person.getUsername());
        } else {
            return "fail";
        }
	}
	
	
	
	

}
