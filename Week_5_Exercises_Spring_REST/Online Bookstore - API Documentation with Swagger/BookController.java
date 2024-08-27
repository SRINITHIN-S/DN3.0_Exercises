package com.example.bookstoreapi.controller;

import com.example.bookstoreapi.dto.BookDTO;
import com.example.bookstoreapi.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Books", description = "API for managing books")
@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Operation(summary = "Get all books", description = "Retrieve a list of all available books")
    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<BookDTO> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @Operation(summary = "Get a book by ID", description = "Retrieve details of a book by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        BookDTO bookDTO = bookService.getBookById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id " + id));
        return ResponseEntity.ok(bookDTO);
    }

    @Operation(summary = "Create a new book", description = "Add a new book to the store")
    @PostMapping
    public ResponseEntity<BookDTO> createBook(@Valid @RequestBody BookDTO bookDTO) {
        BookDTO savedBook = bookService.saveBook(bookDTO);
        return ResponseEntity.status(201).body(savedBook);
    }

    @Operation(summary = "Update a book", description = "Update details of an existing book")
    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @Valid @RequestBody BookDTO bookDTO) {
        BookDTO updatedBook = bookService.updateBook(id, bookDTO);
        return ResponseEntity.ok(updatedBook);
    }

    @Operation(summary = "Delete a book", description = "Remove a book from the store")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
