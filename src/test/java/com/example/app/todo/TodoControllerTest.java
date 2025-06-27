package com.example.app.todo;

import com.example.app.Application;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class TodoControllerTest {
  
  @Autowired
  private MockMvc mockMvc;

  @Test
  public void getTodos_returnsJspView() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/todos"))
      .andExpect(status().isOk())
      .andExpect(view().name("todos"))
      .andExpect(model().attributeExists("todos"));
  }

  @Test
  public void createTodo_andGetTodos_modelContainsNewTodo() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/todos")
      .contentType(MediaType.APPLICATION_FORM_URLENCODED)
      .param("text", "MockMvcTestTodo"));

    mockMvc.perform(MockMvcRequestBuilders.get("/todos"))
      .andExpect(status().isOk())
      .andExpect(model().attributeExists("todos"))
      .andExpect(model().attribute("todos", org.hamcrest.Matchers.hasItem(
        org.hamcrest.Matchers.hasProperty("text", org.hamcrest.Matchers.is("MockMvcTestTodo"))
      )));
  }
}