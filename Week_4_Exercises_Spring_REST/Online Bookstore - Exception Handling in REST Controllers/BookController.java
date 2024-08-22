package com.example.bookstoreapi.controller;

import com.example.bookstoreapi.exception.ResourceNotFoundException;
import com.example.bookstoreapi.model.Book;
import com.example.bookstoreapi.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    // GET /api/books - Get all books
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    // GET /api/books/{id} - Get a book by ID
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + id, "/api/books/" + id));
        return ResponseEntity.ok(book);
    }

    // POST /api/books - Create a new book with custom headers
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book savedBook = bookService.saveBook(book);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/api/books/" + savedBook.getId()));
        headers.add("X-Custom-Header", "BookCreated");

        return new ResponseEntity<>(savedBook, headers, HttpStatus.CREATED);
    }

    // PUT /api/books/{id} - Update an existing book with custom headers
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
        Book book = bookService.getBookById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + id, "/api/books/" + id));

        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setPrice(bookDetails.getPrice());
        book.setIsbn(bookDetails.getIsbn());

        Book updatedBook = bookService.saveBook(book);

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Custom-Header", "BookUpdated");

        return new ResponseEntity<>(updatedBook, headers, HttpStatus.OK);
    }

    // DELETE /api/books/{id} - Delete a book
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        Book book = bookService.getBookById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + id, "/api/books/" + id));

        bookService.deleteBook(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
