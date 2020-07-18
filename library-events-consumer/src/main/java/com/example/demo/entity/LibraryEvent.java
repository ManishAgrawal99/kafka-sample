package com.example.demo.entity;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document(collection = "LibraryEvent")
public class LibraryEvent {

	@Id
	private String libraryEventId;
	
	@NotNull
	@Valid
	@DBRef
	private Book book;
	
	private LibraryEventType libraryEventType;

	public LibraryEvent() {
		super();
	}

	public LibraryEvent(String libraryEventId, Book book, LibraryEventType libraryEventType) {
		super();
		this.libraryEventId = libraryEventId;
		this.book = book;
		this.libraryEventType = libraryEventType;
	}

	@Override
	public String toString() {
		return "LibraryEvent [libraryEventId=" + libraryEventId + ", book=" + book + "]";
	}

	public String getLibraryEventId() {
		return libraryEventId;
	}

	public void setLibraryEventId(String libraryEventId) {
		this.libraryEventId = libraryEventId;
	}

	public LibraryEventType getLibraryEventType() {
		return libraryEventType;
	}

	public void setLibraryEventType(LibraryEventType libraryEventType) {
		this.libraryEventType = libraryEventType;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

}
