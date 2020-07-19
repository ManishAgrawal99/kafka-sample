package com.example.demo.service;

import javax.validation.constraints.Null;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Book;
import com.example.demo.entity.LibraryEvent;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.LibraryEventRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LibraryEventsService {
	
	@Autowired
	private LibraryEventRepository eventRepository;
	
	@Autowired
	private BookRepository bookRepository;
	
	Logger logger = LoggerFactory.getLogger(LibraryEventsService.class);
	
	@Autowired
	ObjectMapper objectMapper;
	
	public void processLibraryEvent(ConsumerRecord<String, String> consumerRecord) throws JsonMappingException, JsonProcessingException {
		
		LibraryEvent libraryEvent = objectMapper.readValue(consumerRecord.value(), LibraryEvent.class);
		
		logger.info("LibraryEvent: {}", libraryEvent);
		
		if(libraryEvent.getLibraryEventId() != null) {
			throw new RecoverableDataAccessException("Temporary Network issue");
		}
		
		switch (libraryEvent.getLibraryEventType()) {
		case NEW:
			save(libraryEvent);
			break;
			
		case UPDATE:
			
			break;
			
		default:
			logger.info("Invalid Library Event Type");
			break;
		}
		
	}

	private void save(LibraryEvent libraryEvent) {
		Book savedBook = bookRepository.save(libraryEvent.getBook());
		
		LibraryEvent event = libraryEvent;
		event.setBook(savedBook);
		 
		LibraryEvent savedEvent = eventRepository.save(event);
		
		logger.info("Successfully Persisted the event: {}", savedEvent);
		
	}
}
