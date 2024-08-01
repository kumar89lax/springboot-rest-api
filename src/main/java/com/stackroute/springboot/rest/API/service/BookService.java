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
    //Create a Arraylist with the name 'books'


    public List<Book> getAllBooks() {
       //Write code to get all book details
        return null;
    }

    public Optional<Book> getBookById(String id) {
        //Write code to get book details by id
        return null;
    }

    public Book addBook(Book book) {
        //Write code to add books in the arraylist
        return null;
    }

    public Book updateBook(String id, Book updatedBook) {
        //Write code to update book details
        return null;
    }

    public void deleteBook(String id) {
        //Write code to delete book details by id

    }
}

