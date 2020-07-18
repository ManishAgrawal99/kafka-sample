package com.example.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.entity.LibraryEvent;

public interface LibraryEventRepository extends MongoRepository<LibraryEvent, String> {
	
}
