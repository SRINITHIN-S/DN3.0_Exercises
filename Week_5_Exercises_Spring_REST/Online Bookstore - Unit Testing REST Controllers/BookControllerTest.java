package com.example.bookstoreapi.controller;

import com.example.bookstoreapi.dto.BookDTO;
import com.example.bookstoreapi.model.BookModel;
import com.example.bookstoreapi.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    private BookDTO bookDTO;

    @BeforeEach
    void setUp() {
        bookDTO = new BookDTO(1L, "Effective Java", "Joshua Bloch", 45.00, "9780134685991");
    }

    @Test
    void testGetAllBooks() throws Exception {
        given(bookService.getAllBooks()).willReturn(Arrays.asList(bookDTO));

        mockMvc.perform(get("/api/books")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.bookModelList[0].title").value("Effective Java"))
                .andExpect(jsonPath("$._embedded.bookModelList[0].author").value("Joshua Bloch"));
    }

    @Test
    void testGetBookById() throws Exception {
        given(bookService.getBookById(anyLong())).willReturn(Optional.of(bookDTO));

        mockMvc.perform(get("/api/books/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Effective Java"))
                .andExpect(jsonPath("$.author").value("Joshua Bloch"));
    }

    @Test
    void testCreateBook() throws Exception {
        given(bookService.saveBook(any(BookDTO.class))).willReturn(bookDTO);

        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Effective Java"))
                .andExpect(jsonPath("$.author").value("Joshua Bloch"));
    }

    @Test
    void testUpdateBook() throws Exception {
        given(bookService.getBookById(anyLong())).willReturn(Optional.of(bookDTO));
        given(bookService.saveBook(any(BookDTO.class))).willReturn(bookDTO);

        mockMvc.perform(put("/api/books/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Effective Java"))
                .andExpect(jsonPath("$.author").value("Joshua Bloch"));
    }

    @Test
    void testDeleteBook() throws Exception {
        given(bookService.getBookById(anyLong())).willReturn(Optional.of(bookDTO));

        mockMvc.perform(delete("/api/books/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}
