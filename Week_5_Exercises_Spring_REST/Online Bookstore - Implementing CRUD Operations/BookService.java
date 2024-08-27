package com.example.bookstoreapi.service;

import com.example.bookstoreapi.model.Book;
import com.example.bookstoreapi.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.OptimisticLockException;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Book saveBook(Book book) {
        try {
            return bookRepository.save(book);
        } catch (OptimisticLockException e) {
            throw new RuntimeException("Concurrent update detected. Please reload and try again.");
        }
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
