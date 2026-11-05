package com.example.spring_security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.spring_security.model.Person;

@Repository
public interface CollegeRepository extends JpaRepository<Person, Integer> {
	
	Person findByUsername(String username);

}
