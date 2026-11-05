package com.example.spring_security.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.spring_security.model.Person;

@Service
public class KafkaConsumerService {

    private String message;
	
	@KafkaListener(topics= "telusko" , groupId = "telusko-group")
	public void consume(Person person)
	{
		message = person + " Got the person data from kafka server";
	//	System.out.println(message);
	}

	public String getMessage() {
		return message;
	}
}
