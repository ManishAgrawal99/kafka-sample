package com.example.demo.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

//@Component
@Slf4j
public class LibraryEventsConsumerManualOffset implements AcknowledgingMessageListener<Integer, String> {

	Logger logger = LoggerFactory.getLogger(LibraryEventsConsumerManualOffset.class);


	@Override
	@KafkaListener(topics = { "library-events" })
	public void onMessage(ConsumerRecord<Integer, String> data, Acknowledgment acknowledgment) {
		logger.info("Consumer Record: {}", data);
		
		acknowledgment.acknowledge();
		
	}
}
