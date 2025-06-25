package com.example.app.todo;

import com.example.app.Application;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

// Integration test that does not use MockMvc
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TodoControllerITest {
  
  @Autowired
  private TestRestTemplate template;

  @Test
  public void getTodo() throws Exception {
    ResponseEntity<String> response = template.getForEntity("/", String.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isEqualTo("Greetings from Spring Boot TodoController!");
  }
}
