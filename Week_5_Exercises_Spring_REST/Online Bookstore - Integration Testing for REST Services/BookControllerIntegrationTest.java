package com.example.bookstoreapi.integration;

import com.example.bookstoreapi.dto.BookDTO;
import com.example.bookstoreapi.model.Book;
import com.example.bookstoreapi.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private BookDTO bookDTO;

    @BeforeEach
    void setUp() {
        bookDTO = new BookDTO(null, "Clean Code", "Robert C. Martin", 40.00, "9780132350884");
    }

    @Test
    void testCreateBook() throws Exception {
        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Clean Code"))
                .andExpect(jsonPath("$.author").value("Robert C. Martin"));

        Optional<Book> createdBook = bookRepository.findByIsbn("9780132350884");
        assertTrue(createdBook.isPresent());
    }

    @Test
    void testGetAllBooks() throws Exception {
        Book book = new Book(null, "Effective Java", "Joshua Bloch", 45.00, "9780134685991");
        bookRepository.save(book);

        mockMvc.perform(get("/api/books")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.bookModelList", hasSize(1)))
                .andExpect(jsonPath("$._embedded.bookModelList[0].title", is("Effective Java")))
                .andExpect(jsonPath("$._embedded.bookModelList[0].author", is("Joshua Bloch")));
    }

    @Test
    void testGetBookById() throws Exception {
        Book savedBook = bookRepository.save(new Book(null, "Effective Java", "Joshua Bloch", 45.00, "9780134685991"));

        mockMvc.perform(get("/api/books/{id}", savedBook.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Effective Java")))
                .andExpect(jsonPath("$.author", is("Joshua Bloch")));
    }

    @Test
    void testUpdateBook() throws Exception {
        Book savedBook = bookRepository.save(new Book(null, "Effective Java", "Joshua Bloch", 45.00, "9780134685991"));
        BookDTO updatedBookDTO = new BookDTO(null, "Effective Java (2nd Edition)", "Joshua Bloch", 50.00, "9780134685991");

        mockMvc.perform(put("/api/books/{id}", savedBook.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedBookDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Effective Java (2nd Edition)"))
                .andExpect(jsonPath("$.price").value(50.00));
    }

    @Test
    void testDeleteBook() throws Exception {
        Book savedBook = bookRepository.save(new Book(null, "Effective Java", "Joshua Bloch", 45.00, "9780134685991"));

        mockMvc.perform(delete("/api/books/{id}", savedBook.getId()))
                .andExpect(status().isNoContent());

        assertTrue(bookRepository.findById(savedBook.getId()).isEmpty());
    }
}
