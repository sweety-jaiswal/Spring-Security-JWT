package com.example.spring_security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.spring_security.model.Person;

@Service
public class KafkaProducerService {
	
	public static final String TOPIC = "telusko";

    @Autowired
    private KafkaTemplate<String, Person> kafkaTemplate;

    public String sendMessage(Person person) {
        kafkaTemplate.send(TOPIC, "firstKey", person);
        return "Person details(message) sent to kafka server successfully: " + person;
    }

}
