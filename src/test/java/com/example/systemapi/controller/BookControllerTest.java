package com.example.systemapi.controller;

import com.example.systemapi.persistence.dto.Book;
import com.example.systemapi.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

  @Mock
  private BookService service;

  @InjectMocks
  private BookController controller;

  @Test
  public void getBooks() {
    //when
    controller.getBooks();

    //then
    verify(service).getBooks();
  }

  @Test
  public void whenServiceReturnsBook_thenControllerReturns200WithBookObject() {
    //given
    Book sampleBook = new Book();
    when(service.getBook(anyString()))
        .thenReturn(Optional.of(sampleBook));

    //when
    ResponseEntity<Book> bookResponseEntity = controller.getBook("", false);

    //then
    assertEquals(HttpStatus.OK, bookResponseEntity.getStatusCode());
    assertSame(sampleBook, bookResponseEntity.getBody());
  }

  @Test
  public void givenStrictFlagIsFalse_whenServiceDoesNotReturnBook_thenControllerReturns204() {
    //when
    ResponseEntity<Book> bookResponseEntity = controller.getBook("", false);

    //then
    assertEquals(HttpStatus.NO_CONTENT, bookResponseEntity.getStatusCode());
    assertNull(bookResponseEntity.getBody());
  }

  @Test
  public void givenStrictFlagIsTrue_whenServiceDoesNotReturnBook_thenControllerReturns404() {
    //when
    ResponseEntity<Book> bookResponseEntity = controller.getBook("", true);

    //then
    assertEquals(HttpStatus.NOT_FOUND, bookResponseEntity.getStatusCode());
    assertNull(bookResponseEntity.getBody());
  }

  @Test
  public void postBook() {
    //given
    Book sampleBook = new Book();

    //when
    controller.postBook(sampleBook);

    //then
    verify(service).postBook(sampleBook);
  }

  @Test
  public void whenBookIsDeleted_thenControllerReturns204() {
    //given
    when(service.deleteBook(anyString()))
        .thenReturn(true);

    //when
    ResponseEntity<Void> responseEntity = controller.deleteBook("", false);

    //then
    assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
  }

  @Test
  public void givenStrictFlagIsFalse_whenBookIsNotDeleted_thenControllerReturns200() {
    //given
    when(service.deleteBook(anyString()))
        .thenReturn(false);

    //when
    ResponseEntity<Void> responseEntity = controller.deleteBook("", false);

    //then
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
  }

  @Test
  public void givenStrictFlagIsTrue_whenBookIsNotDeleted_thenControllerReturns200() {
    //given
    when(service.deleteBook(anyString()))
        .thenReturn(false);

    //when
    ResponseEntity<Void> responseEntity = controller.deleteBook("", true);

    //then
    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
  }
}