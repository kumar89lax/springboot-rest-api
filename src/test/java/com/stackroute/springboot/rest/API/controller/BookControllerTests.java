package com.stackroute.springboot.rest.API.controller;

import com.stackroute.springboot.rest.API.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetAllBooks() {
        ResponseEntity<Book[]> response = restTemplate.getForEntity("/books", Book[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void testAddBook() {
        Book book = new Book(null, "Test Title", "Test Author", "Test Genre", 9.99);
        ResponseEntity<Book> response = restTemplate.postForEntity("/books", book, Book.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();
    }

    @Test
    public void testGetBookById() {
        Book book = new Book(null, "Test Title", "Test Author", "Test Genre", 9.99);
        ResponseEntity<Book> createResponse = restTemplate.postForEntity("/books", book, Book.class);
        String bookId = createResponse.getBody().getId();

        ResponseEntity<Book> getResponse = restTemplate.getForEntity("/books/" + bookId, Book.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().getId()).isEqualTo(bookId);
    }

    @Test
    public void testUpdateBook() {
        Book book = new Book(null, "Test Title", "Test Author", "Test Genre", 9.99);
        ResponseEntity<Book> createResponse = restTemplate.postForEntity("/books", book, Book.class);
        String bookId = createResponse.getBody().getId();

        Book updatedBook = new Book(bookId, "Updated Title", "Updated Author", "Updated Genre", 19.99);
        restTemplate.put("/books/" + bookId, updatedBook);

        ResponseEntity<Book> getResponse = restTemplate.getForEntity("/books/" + bookId, Book.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().getTitle()).isEqualTo("Updated Title");
    }

    @Test
    public void testDeleteBook() {
        Book book = new Book(null, "Test Title", "Test Author", "Test Genre", 9.99);
        ResponseEntity<Book> createResponse = restTemplate.postForEntity("/books", book, Book.class);
        String bookId = createResponse.getBody().getId();

        restTemplate.delete("/books/" + bookId);

        ResponseEntity<Book> getResponse = restTemplate.getForEntity("/books/" + bookId, Book.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}

