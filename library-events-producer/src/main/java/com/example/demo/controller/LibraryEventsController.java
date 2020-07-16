package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.LibraryEvent;
import com.example.demo.domain.LibraryEventType;
import com.example.demo.producer.LibraryEventProducer;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;


@RestController
public class LibraryEventsController {
	
	Logger logger = LoggerFactory.getLogger(LibraryEventsController.class);
	
	@Autowired
	LibraryEventProducer libraryEventProducer;
	
	@PostMapping("/v1/libraryEvent")
	public ResponseEntity<LibraryEvent> postLibraryEvent(@RequestBody @Valid LibraryEvent libraryEvent) throws JsonProcessingException{
		
		libraryEvent.setLibraryEventType(LibraryEventType.NEW);
		
		//invoke kafka producer
		System.out.println(libraryEvent);
		
//		libraryEventProducer.sendLibraryEvent(libraryEvent);
		libraryEventProducer.sendLibraryEvent_Approach2(libraryEvent);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);
	}
	
	
	@PostMapping("/v1/libraryEventSynchronous")
	public ResponseEntity<LibraryEvent> postLibraryEventSynchronous(@RequestBody LibraryEvent libraryEvent) throws Exception{
		
		//invoke kafka producer
		System.out.println(libraryEvent);
		
		SendResult<Integer, String> sendResult = libraryEventProducer.sendLibraryEventSynchronous(libraryEvent);
		
		logger.info("Send Result is {}", sendResult.toString());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);
	}
	
	
}
