package com.stackroute.springboot.rest.API.repo;

import com.stackroute.springboot.rest.API.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepo extends JpaRepository<Book, Long> {
}