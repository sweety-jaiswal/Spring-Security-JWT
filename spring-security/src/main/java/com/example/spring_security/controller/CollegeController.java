package com.example.spring_security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring_security.model.Person;
import com.example.spring_security.service.CollegeService;
import com.example.spring_security.service.KafkaConsumerService;
import com.example.spring_security.service.KafkaProducerService;

@RestController
public class CollegeController {
	
	@Autowired
	CollegeService collegeService;
	
	@Autowired
	private KafkaConsumerService consumerService;
	
	@Autowired
	private KafkaProducerService producerService;
	
	@GetMapping("/home")
	public String homePage() {
		return "Welcome to the PLATFORM!.";
	}
	
	@PostMapping("/login")
    public String login(@RequestBody Person person) {

        return collegeService.verify(person);
    }
	
	@GetMapping("/student")
	public String studentSection() {
		return "Welcome to the STUDENT SECTION!.";
	}
	
	@GetMapping("/admin")
	public String adminSection() {
		return "Welcome to the ADMIN SECTION!.";
	}
 
	@PostMapping("/signupAsStudent")
	public String addPerson(@RequestBody Person person) {
		return collegeService.addPerson(person);
	}
	
	@PostMapping("/signupAsAdmin")
	public String addAdmin(@RequestBody Person person) {
		return collegeService.addAdmin(person);
	}
	
	@GetMapping("/check-shared-endpoint")
	public String getDetails() {
		return "Both roles can access";
	}
	
	@PostMapping("/add-data-to-kafka")
	public ResponseEntity<String> sendMessage(@RequestBody Person person)
	{
		String response=producerService.sendMessage(person);
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
	
	@GetMapping("/get-data-from-kafka")
	public ResponseEntity<String> getMessage()
	{
		String response=consumerService.getMessage();
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
	
}
