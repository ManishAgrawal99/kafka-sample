package com.example.demo.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LibraryEventsConsumer {
	
	Logger logger = LoggerFactory.getLogger(LibraryEventsConsumer.class);
	
	@KafkaListener(topics = {"library-events"})
	public void onMessage(ConsumerRecord<Integer, String> consumerRecord) {
		
		logger.info("Consumer Record: {}", consumerRecord);
		
		
	}
}
