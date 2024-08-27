package com.example.bookstoreapi.model;

import com.example.bookstoreapi.dto.BookDTO;
import org.springframework.hateoas.RepresentationModel;

public class BookModel extends RepresentationModel<BookModel> {

    private Long id;
    private String title;
    private String author;
    private double price;
    private String isbn;

    // Constructors, getters, and setters

    public BookModel(BookDTO bookDTO) {
        this.id = bookDTO.getId();
        this.title = bookDTO.getTitle();
        this.author = bookDTO.getAuthor();
        this.price = bookDTO.getPrice();
        this.isbn = bookDTO.getIsbn();
    }

    // Getters and setters
}
