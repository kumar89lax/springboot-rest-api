package com.stackroute.springboot.rest.API.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Optional;

import com.stackroute.springboot.rest.API.exception.BookNotFoundException;
import com.stackroute.springboot.rest.API.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BookServiceTests {

    private BookService bookService;

    @BeforeEach
    public void setUp() {
        bookService = new BookService();
    }

    @Test
    public void testAddBook() {
        Book book = new Book(null, "Test Title", "Test Author", "Test Genre", 9.99);
        Book savedBook = bookService.addBook(book);

        assertThat(savedBook.getId()).isNotNull();
        assertThat(bookService.getAllBooks()).hasSize(1);
    }

    @Test
    public void testGetAllBooks() {
        Book book1 = new Book(null, "Test Title 1", "Test Author 1", "Test Genre 1", 9.99);
        Book book2 = new Book(null, "Test Title 2", "Test Author 2", "Test Genre 2", 19.99);
        bookService.addBook(book1);
        bookService.addBook(book2);

        List<Book> books = bookService.getAllBooks();
        assertThat(books).hasSize(2);
    }

    @Test
    public void testGetBookById() {
        Book book = new Book(null, "Test Title", "Test Author", "Test Genre", 9.99);
        Book savedBook = bookService.addBook(book);

        Optional<Book> retrievedBook = bookService.getBookById(savedBook.getId());
        assertThat(retrievedBook).isPresent();
        assertThat(retrievedBook.get().getId()).isEqualTo(savedBook.getId());
    }

    @Test
    public void testUpdateBook() {
        Book book = new Book(null, "Test Title", "Test Author", "Test Genre", 9.99);
        Book savedBook = bookService.addBook(book);

        Book updatedBook = new Book(savedBook.getId(), "Updated Title", "Updated Author", "Updated Genre", 19.99);
        Book result = bookService.updateBook(savedBook.getId(), updatedBook);

        assertThat(result.getTitle()).isEqualTo("Updated Title");
    }

    @Test
    public void testDeleteBook() {
        Book book = new Book(null, "Test Title", "Test Author", "Test Genre", 9.99);
        Book savedBook = bookService.addBook(book);

        bookService.deleteBook(savedBook.getId());
        assertThat(bookService.getAllBooks()).isEmpty();
    }

    @Test
    public void testDeleteBook_NotFound() {
        assertThrows(BookNotFoundException.class, () -> {
            bookService.deleteBook("non-existent-id");
        });
    }

    @Test
    public void testUpdateBook_NotFound() {
        Book updatedBook = new Book("non-existent-id", "Updated Title", "Updated Author", "Updated Genre", 19.99);
        assertThrows(BookNotFoundException.class, () -> {
            bookService.updateBook("non-existent-id", updatedBook);
        });
    }
}

