package com.example.demo;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.controller.LibraryEventsController;
import com.example.demo.domain.Book;
import com.example.demo.domain.LibraryEvent;
import com.example.demo.producer.LibraryEventProducer;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(LibraryEventsController.class)
@AutoConfigureMockMvc
public class LibraryEventControllerUnitTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	LibraryEventProducer libraryEventProducer;
	
	ObjectMapper objectMapper =new ObjectMapper();
	
	@Test
	void postLibraryEvent() throws Exception {
		
		//given
		Book book = new Book(123, "Dilip", "Kafka using Spring Boot");
		
		LibraryEvent libraryEvent = new LibraryEvent(null, book, null);
		
		String json = objectMapper.writeValueAsString(libraryEvent);
		
		//doNothing().when(libraryEventProducer).sendLibraryEvent_Approach2();
		
		//When
		
		mockMvc.perform(post("/v1/libraryEvent").content(json).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
	}
}
