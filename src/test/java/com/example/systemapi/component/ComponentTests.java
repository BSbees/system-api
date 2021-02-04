package com.example.systemapi.component;

import com.example.systemapi.persistence.dto.Book;
import com.example.systemapi.persistence.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ComponentTests {

  @LocalServerPort
  private int port;

  @Autowired
  private BookRepository repository;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  void getBooks() {
    //given
    repository.save(getRandomBook());
    repository.save(getRandomBook());
    repository.save(getRandomBook());

    //when
    List response = restTemplate.getForObject("http://localhost:" + port + "/book", List.class);

    //then
    assertEquals(3, response.size());
  }

  @Test
  void getBook() {
    //given
    Book expectedBook = getRandomBook();
    repository.save(expectedBook);
    repository.save(getRandomBook());
    repository.save(getRandomBook());
    repository.save(getRandomBook());

    //when
    Book actualBook = restTemplate.getForObject("http://localhost:" + port + "/book/" + expectedBook.getIsbn(), Book.class);

    //then
    assertEquals(expectedBook.getTitle(), actualBook.getTitle());
  }

  @Test
  void postBook() {
    //given
    Book bookRequest = getRandomBook();

    //when
    Book response = restTemplate.postForObject("http://localhost:" + port + "/book", bookRequest, Book.class);

    //then
    assertEquals(repository.findById(response.getIsbn()).get(), bookRequest);
  }

  @Test
  void deleteBook() {
    //given
    Book expectedBook = getRandomBook();
    repository.save(expectedBook);
    repository.save(getRandomBook());
    repository.save(getRandomBook());
    repository.save(getRandomBook());

    //when
    restTemplate.delete("http://localhost:" + port + "/book/" + expectedBook.getIsbn());

    //then
    assertFalse(repository.existsById(expectedBook.getIsbn()));
    assertEquals(3, repository.findAll().size());
  }

  @BeforeEach
  public void clearRepository() {
    repository.deleteAll();
  }

  private Book getRandomBook() {
    return Book.builder()
        .isbn(UUID.randomUUID().toString())
        .title(UUID.randomUUID().toString())
        .build();
  }
}
