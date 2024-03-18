package com.reactive.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.reactive.entity.Book;

@Repository
public interface BookRepository  extends ReactiveCrudRepository<Book, Integer>{

}
