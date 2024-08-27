package com.example.bookstoreapi.controller;

import com.example.bookstoreapi.dto.BookDTO;
import com.example.bookstoreapi.mapper.BookMapper;
import com.example.bookstoreapi.model.Book;
import com.example.bookstoreapi.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
@Validated
public class BookController {

    @Autowired
    private BookService bookService;

    private final BookMapper bookMapper = BookMapper.INSTANCE;

    // GET /api/books - Get all books
    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<BookDTO> books = bookService.getAllBooks().stream()
                .map(bookMapper::bookToBookDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(books);
    }

    // GET /api/books/{id} - Get a book by ID
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id " + id));
        BookDTO bookDTO = bookMapper.bookToBookDTO(book);
        return ResponseEntity.ok(bookDTO);
    }

    // POST /api/books - Create a new book
    @PostMapping
    public ResponseEntity<BookDTO> createBook(@Valid @RequestBody BookDTO bookDTO) {
        Book book = bookMapper.bookDTOToBook(bookDTO);
        Book savedBook = bookService.saveBook(book);
        BookDTO savedBookDTO = bookMapper.bookToBookDTO(savedBook);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/api/books/" + savedBookDTO.getId()));
        return new ResponseEntity<>(savedBookDTO, headers, HttpStatus.CREATED);
    }

    // PUT /api/books/{id} - Update an existing book
    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @Valid @RequestBody BookDTO bookDTO) {
        Book book = bookService.getBookById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id " + id));

        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setPrice(bookDTO.getPrice());
        book.setIsbn(bookDTO.getIsbn());

        Book updatedBook = bookService.saveBook(book);
        BookDTO updatedBookDTO = bookMapper.bookToBookDTO(updatedBook);

        return ResponseEntity.ok(updatedBookDTO);
    }

    // DELETE /api/books/{id} - Delete a book
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        Book book = bookService.getBookById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id " + id));
        bookService.deleteBook(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
