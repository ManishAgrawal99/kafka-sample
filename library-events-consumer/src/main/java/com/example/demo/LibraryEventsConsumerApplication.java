package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
public class LibraryEventsConsumerApplication {

	public static void main(String[] args) {	
		SpringApplication.run(LibraryEventsConsumerApplication.class, args);
	}

}
