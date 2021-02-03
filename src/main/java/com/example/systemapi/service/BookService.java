package com.example.systemapi.service;

import com.example.systemapi.persistence.dto.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
  List<Book> getBooks();

  Optional<Book> getBook(String isbn);

  Book postBook(Book book);

  boolean deleteBook(String isbn);
}
