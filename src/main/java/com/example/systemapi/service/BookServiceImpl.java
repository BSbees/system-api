package com.example.systemapi.service;

import com.example.systemapi.persistence.dto.Book;
import com.example.systemapi.persistence.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

  private final BookRepository repository;

  @Override
  public List<Book> getBooks() {
    return repository.findAll();
  }

  @Override
  public Optional<Book> getBook(String isbn) {
    return repository.findById(isbn);
  }

  @Override
  public Book postBook(Book book) {
    return repository.save(book);
  }

  @Override
  public boolean deleteBook(String isbn) {
    Optional<Book> book = repository.findById(isbn);
    book.ifPresent(repository::delete);
    return book.isPresent();
  }
}
