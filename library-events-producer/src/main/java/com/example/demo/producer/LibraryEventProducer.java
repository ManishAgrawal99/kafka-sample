package com.example.demo.producer;

import com.example.demo.domain.LibraryEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@Slf4j
public class LibraryEventProducer {

	Logger logger = LoggerFactory.getLogger(LibraryEventProducer.class);
	

    @Autowired
    KafkaTemplate<Integer,String> kafkaTemplate;

    @Autowired
    ObjectMapper objectMapper;

    public void sendLibraryEvent(LibraryEvent libraryEvent) throws JsonProcessingException {

        Integer key = libraryEvent.getLibraryEventId();
        String value = objectMapper.writeValueAsString(libraryEvent);

        ListenableFuture<SendResult<Integer,String>> listenableFuture =  kafkaTemplate.sendDefault(key,value);
        
        listenableFuture.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                handleFailure(key, value, ex);
            }

            @Override
            public void onSuccess(SendResult<Integer, String> result) {
                handleSuccess(key, value, result);
            }
        });

    }
    
    
    public void sendLibraryEvent_Approach2(LibraryEvent libraryEvent) throws JsonProcessingException {

        Integer key = libraryEvent.getLibraryEventId();
        String value = objectMapper.writeValueAsString(libraryEvent);
        
        String topic = "library-events";
        
        ProducerRecord<Integer, String> producerRecord = buildProducerRecord(key, value, topic);

        ListenableFuture<SendResult<Integer,String>> listenableFuture =  kafkaTemplate.send(producerRecord);
        
        listenableFuture.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                handleFailure(key, value, ex);
            }

            @Override
            public void onSuccess(SendResult<Integer, String> result) {
                handleSuccess(key, value, result);
            }
        });

    }
    
    
    
    private ProducerRecord<Integer, String> buildProducerRecord(Integer key, String value, String topic) {

    	List<Header> recordHeaders = new ArrayList<>();
    	
    	Header h1 = new RecordHeader("event-source", "scanner".getBytes());
    	
    	recordHeaders.add(h1);
    	
    	return new ProducerRecord<>(topic, null, key, value, recordHeaders);
	}


	public SendResult<Integer, String> sendLibraryEventSynchronous(LibraryEvent libraryEvent) throws Exception {
    	
    	
    	Integer key = libraryEvent.getLibraryEventId();
        String value = objectMapper.writeValueAsString(libraryEvent);
        
        SendResult<Integer,String> sendResult = null;
        
        try {
			sendResult = kafkaTemplate.sendDefault(key,value).get();
			
		} catch (InterruptedException | ExecutionException e) {
			
			logger.error("InterruptedException | ExecutionException Sending the Message and the exception is {}", e.getMessage());
			throw e;
			
		} catch (Exception e) {
			
			logger.error("Error Sending the Message and the exception is {}", e.getMessage());
		}
        
        return sendResult;
    }
    

    private void handleFailure(Integer key, String value, Throwable ex) {
    	logger.error("Error Sending the Message and the exception is {}", ex.getMessage());
        try {
            throw ex;
        } catch (Throwable throwable) {
        	logger.error("Error in OnFailure: {}", throwable.getMessage());
        }


    }

    private void handleSuccess(Integer key, String value, SendResult<Integer, String> result) {
    	logger.info("Message Sent SuccessFully for the key : {} and the value is {} , partition is {}", key, value, result.getRecordMetadata().partition());
    }
}
