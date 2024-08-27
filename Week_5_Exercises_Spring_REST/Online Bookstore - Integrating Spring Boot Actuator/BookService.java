package com.example.bookstoreapi.service;

import com.example.bookstoreapi.model.Book;
import com.example.bookstoreapi.repository.BookRepository;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Counter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    private final Counter booksCreatedCounter;

    @Autowired
    public BookService(MeterRegistry meterRegistry) {
        this.booksCreatedCounter = meterRegistry.counter("books.created.count");
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Book saveBook(Book book) {
        booksCreatedCounter.increment();  // Increment custom metric
        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
