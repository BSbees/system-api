package com.example.systemapi.persistence.repository;

import com.example.systemapi.persistence.dto.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, String> {
}
