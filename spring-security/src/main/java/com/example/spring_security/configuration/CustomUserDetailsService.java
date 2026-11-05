package com.example.spring_security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.spring_security.model.Person;
import com.example.spring_security.repository.CollegeRepository;

public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	CollegeRepository collegeRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Person person= collegeRepo.findByUsername(username);
		
		if(person == null)
			throw new RuntimeException("Invalid username");
		
		//  to convert Person object to UserDetails object by making custom class which is implementing UserDetails
		return new CustomUserDetails(person);
	}

	
}
