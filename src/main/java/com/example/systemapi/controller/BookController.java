package com.example.systemapi.controller;

import com.example.systemapi.persistence.dto.Book;
import com.example.systemapi.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("book")
@RequiredArgsConstructor
public class BookController {

  private final BookService service;

  @GetMapping
  public List<Book> getBooks() {
    return service.getBooks();
  }

  @GetMapping("/{isbn}")
  public ResponseEntity<Book> getBook(@PathVariable String isbn,
                                      @RequestParam(defaultValue = "false") boolean strict) {
    Optional<ResponseEntity<Book>> bookResponse = service.getBook(isbn).map(ResponseEntity::ok);
    if (strict) {
      return bookResponse
          .orElse(ResponseEntity.notFound().build());
    }
    return bookResponse
        .orElse(ResponseEntity.noContent().build());
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Book postBook(@RequestBody Book book) {
    return service.postBook(book);
  }

  @DeleteMapping
  public ResponseEntity<Void> deleteBook(@PathVariable String isbn,
                                         @RequestParam(defaultValue = "false") boolean strict) {
    boolean deleted = service.deleteBook(isbn);
    if (deleted) {
      return ResponseEntity.noContent().build();
    }
    if (strict) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok().build();
  }
}
