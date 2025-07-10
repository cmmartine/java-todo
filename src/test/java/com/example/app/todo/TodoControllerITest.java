package com.example.app.todo;

import com.example.app.Application;
import com.example.app.todo.repository.TodoRepository;
import com.example.app.todo.model.Todo;

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

  @Autowired
  private TodoRepository todoRepository;

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

  @Test
  public void deleteTodo() throws Exception{
    template.postForEntity(
      "http://localhost:" + port + "/todos?text=toBeDeleted",
      null,
      String.class
    );

    Todo created = todoRepository.findAll().stream()
      .filter(t -> "toBeDeleted".equals(t.getText()))
      .findFirst()
      .orElse(null);
    assertThat(created).isNotNull();

    template.postForEntity(
      "http://localhost:" + port + "/delete_todo?id=" + String.valueOf(created.getId()),
      null,
      String.class
    );

    assertThat(todoRepository.findById(created.getId())).isEmpty();
  }
}
