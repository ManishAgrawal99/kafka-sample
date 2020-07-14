package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.LibraryEvent;
import com.example.demo.producer.LibraryEventProducer;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
public class LibraryEventsController {
	
	@Autowired
	LibraryEventProducer libraryEventProducer;
	
	@PostMapping("/v1/libraryEvent")
	public ResponseEntity<LibraryEvent> postLibraryEvent(@RequestBody LibraryEvent libraryEvent) throws JsonProcessingException{
		
		//invoke kafka producer
		System.out.println(libraryEvent);
		
		libraryEventProducer.sendLibraryEvent(libraryEvent);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);
	}
	
	
}
