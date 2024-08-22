package com.example.bookstoreapi.repository;

import com.example.bookstoreapi.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
