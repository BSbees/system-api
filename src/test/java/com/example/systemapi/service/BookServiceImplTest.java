package com.example.systemapi.service;

import com.example.systemapi.persistence.dto.Book;
import com.example.systemapi.persistence.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

  @Mock
  private BookRepository repository;

  @InjectMocks
  private BookServiceImpl service;

  @Test
  void getBooks() {
    //when
    service.getBooks();

    //then
    verify(repository).findAll();
  }

  @Test
  void getBook() {
    //given
    String sampleIsbn = "";

    //when
    service.getBook(sampleIsbn);

    //then
    verify(repository).findById(eq(sampleIsbn));
  }

  @Test
  void postBook() {
    //given
    Book sampleBook = new Book();

    //when
    service.postBook(sampleBook);

    //then
    verify(repository).save(sampleBook);
  }

  @Test
  void deleteBook() {
    //given
    String sampleIsbn = "";
    Book sampleBook = new Book();
    when(repository.findById(sampleIsbn))
        .thenReturn(Optional.of(sampleBook));

    //when
    service.deleteBook(sampleIsbn);

    //then
    verify(repository).delete(sampleBook);
  }
}