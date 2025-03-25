package com.stackroute.springboot.rest.API.service;

import com.stackroute.springboot.rest.API.exception.BookNotFoundException;
import com.stackroute.springboot.rest.API.model.Book;
import com.stackroute.springboot.rest.API.repo.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {
    @Autowired
   private BookRepo books;

    public List<Book> getAllBooks() {
        return books.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        return books.findById(id);
    }

    public Book addBook(Book book) {
        return books.save(book);
    }

    public Book updateBook(Long id, Book updatedBook) throws BookNotFoundException {
        Optional<Book> existingBookOpt = getBookById(id);

        if (existingBookOpt.isPresent()) {
            Book existingBook = existingBookOpt.get();
            existingBook.setTitle(updatedBook.getTitle());
            existingBook.setAuthor(updatedBook.getAuthor());
            existingBook.setPrice(updatedBook.getPrice());
            return books.save(existingBook);
        } else {
            throw new BookNotFoundException("Book with ID " + id + " not found");
        }
    }

    public void deleteBook(Long id) throws BookNotFoundException {
        Optional<Book> existingBookOpt = getBookById(id);

        if (existingBookOpt.isPresent()) {
            books.delete(existingBookOpt.get());
        } else {
            throw new BookNotFoundException("Book with ID " + id + " not found");
        }
    }
}