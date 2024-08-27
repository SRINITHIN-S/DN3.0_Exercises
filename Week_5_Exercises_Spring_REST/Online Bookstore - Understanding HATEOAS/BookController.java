package com.example.bookstoreapi.controller;

import com.example.bookstoreapi.dto.BookDTO;
import com.example.bookstoreapi.mapper.BookMapper;
import com.example.bookstoreapi.model.Book;
import com.example.bookstoreapi.model.BookModel;
import com.example.bookstoreapi.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/books")
@Validated
public class BookController {

    @Autowired
    private BookService bookService;

    private final BookMapper bookMapper = BookMapper.INSTANCE;

    // GET /api/books - Get all books
    @GetMapping
    public ResponseEntity<CollectionModel<BookModel>> getAllBooks() {
        List<BookModel> books = bookService.getAllBooks().stream()
                .map(bookMapper::bookToBookDTO)
                .map(BookModel::new)
                .map(this::addBookLinks)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(books));
    }

    // GET /api/books/{id} - Get a book by ID
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<BookModel>> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id " + id));
        BookDTO bookDTO = bookMapper.bookToBookDTO(book);
        BookModel bookModel = new BookModel(bookDTO);
        addBookLinks(bookModel);
        return ResponseEntity.ok(EntityModel.of(bookModel));
    }

    // POST /api/books - Create a new book
    @PostMapping
    public ResponseEntity<BookModel> createBook(@Valid @RequestBody BookDTO bookDTO) {
        Book book = bookMapper.bookDTOToBook(bookDTO);
        Book savedBook = bookService.saveBook(book);
        BookDTO savedBookDTO = bookMapper.bookToBookDTO(savedBook);
        BookModel savedBookModel = new BookModel(savedBookDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/api/books/" + savedBookModel.getId()));

        addBookLinks(savedBookModel);
        return new ResponseEntity<>(savedBookModel, headers, HttpStatus.CREATED);
    }

    // PUT /api/books/{id} - Update an existing book
    @PutMapping("/{id}")
    public ResponseEntity<BookModel> updateBook(@PathVariable Long id, @Valid @RequestBody BookDTO bookDTO) {
        Book book = bookService.getBookById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id " + id));

        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setPrice(bookDTO.getPrice());
        book.setIsbn(bookDTO.getIsbn());

        Book updatedBook = bookService.saveBook(book);
        BookDTO updatedBookDTO = bookMapper.bookToBookDTO(updatedBook);
        BookModel updatedBookModel = new BookModel(updatedBookDTO);

        addBookLinks(updatedBookModel);
        return ResponseEntity.ok(updatedBookModel);
    }

    // DELETE /api/books/{id} - Delete a book
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        Book book = bookService.getBookById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id " + id));
        bookService.deleteBook(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Helper method to add HATEOAS links
    private BookModel addBookLinks(BookModel bookModel) {
        bookModel.add(linkTo(methodOn(BookController.class).getBookById(bookModel.getId())).withSelfRel());
        bookModel.add(linkTo(methodOn(BookController.class).getAllBooks()).withRel("books"));
        return bookModel;
    }
}
