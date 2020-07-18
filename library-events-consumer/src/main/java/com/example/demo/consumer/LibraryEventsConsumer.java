package com.example.demo.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.demo.service.LibraryEventsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LibraryEventsConsumer {
	
	@Autowired
	private LibraryEventsService libraryEventsService; 
	
	Logger logger = LoggerFactory.getLogger(LibraryEventsConsumer.class);
	
	@KafkaListener(topics = {"library-events"})
	public void onMessage(ConsumerRecord<String, String> consumerRecord) throws JsonMappingException, JsonProcessingException {
		
		logger.info("Consumer Record: {}", consumerRecord);
		
		libraryEventsService.processLibraryEvent(consumerRecord);
		
		
	}
}
