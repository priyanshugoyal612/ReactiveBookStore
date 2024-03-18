package com.reactive.service.impl;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reactive.entity.Book;
import com.reactive.repository.BookRepository;
import com.reactive.service.BookService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepository bookRepository;

	@Override
	public Mono<Book> create(Book book) {
		Mono<Book> createdBook = bookRepository.save(book).log();

		return createdBook;
	}

	@Override
	public Flux<Book> getAll() {

		return bookRepository.findAll().delayElements(Duration.ofSeconds(2)).log().map(book -> {
			book.setName(book.getName().toUpperCase());
			return book;
		});

	}

	@Override
	public Mono<Book> get(int bookId) {
		Mono<Book> item = bookRepository.findById(Mono.just(bookId));
		return item;
	}

	@Override
	public Mono<Book> update(Book book, int bookId) {
		Mono<Book> oldBook = bookRepository.findById(bookId);
		return oldBook.flatMap(book1 -> {
			book1.setName(book.getName());
			book1.setPublisher(book.getPublisher());
			book1.setAuthor(book.getAuthor());
			book1.setDescription(book.getDescription());
			return bookRepository.save(book1);
		}

		);

	}

	@Override
	public Mono<Void> delete(int bookId) {
		return bookRepository.findById(bookId).flatMap(book -> bookRepository.delete(book));
	}

	@Override
	public Flux<Book> search(String query) {

		return null;
	}

}
