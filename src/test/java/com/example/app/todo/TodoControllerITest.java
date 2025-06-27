package com.example.app.todo;

import com.example.app.Application;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import static org.assertj.core.api.Assertions.assertThat;

// Integration test that does not use MockMvc
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TodoControllerITest {

  @LocalServerPort
  private int port;
  
  @Autowired
  private TestRestTemplate template;

  @Test
  public void createTodo_andGetTodos_returnsTodosList() throws Exception{
    template.postForEntity(
      "http://localhost:" + port + "/todos?text=IntegrationTestTodo",
      null,
      String.class
    );
    ResponseEntity<String> response = template.getForEntity(
      "http://localhost:" + port + "/todos",
      String.class
    );
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).contains("IntegrationTestTodo");
  }
}
