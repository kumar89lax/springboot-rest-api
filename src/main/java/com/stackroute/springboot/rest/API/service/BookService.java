package com.stackroute.springboot.rest.API.service;

import com.stackroute.springboot.rest.API.exception.BookNotFoundException;
import com.stackroute.springboot.rest.API.model.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {
    private final List<Book> books = new ArrayList<>();

    public List<Book> getAllBooks() {
        return books;
    }

    public Optional<Book> getBookById(String id) {
        return books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst();
    }

    public Book addBook(Book book) {
        book.setId(UUID.randomUUID().toString()); // Generate a unique ID
        books.add(book);
        return book;
    }

    public Book updateBook(String id, Book updatedBook) throws BookNotFoundException {
        Optional<Book> existingBookOpt = getBookById(id);

        if (existingBookOpt.isPresent()) {
            Book existingBook = existingBookOpt.get();
            existingBook.setTitle(updatedBook.getTitle());
            existingBook.setAuthor(updatedBook.getAuthor());
            existingBook.setPrice(updatedBook.getPrice());
            return existingBook;
        } else {
            throw new BookNotFoundException("Book with ID " + id + " not found");
        }
    }

    public void deleteBook(String id) throws BookNotFoundException {
        Optional<Book> existingBookOpt = getBookById(id);

        if (existingBookOpt.isPresent()) {
            books.remove(existingBookOpt.get());
        } else {
            throw new BookNotFoundException("Book with ID " + id + " not found");
        }
    }
}