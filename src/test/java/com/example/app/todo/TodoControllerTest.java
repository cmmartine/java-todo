package com.example.app.todo;

import com.example.app.Application;
import com.example.app.todo.model.Todo;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;

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

  @Test
  public void deleteTodo() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/todos")
      .contentType(MediaType.APPLICATION_FORM_URLENCODED)
      .param("text", "DeleteMe")
    );

    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/todos"))
      .andExpect(status().isOk())
      .andReturn();

    @SuppressWarnings("unchecked")
    List<Todo> todos = (List<Todo>) result.getModelAndView().getModel().get("todos");
    Todo created = todos.stream().filter(t -> "DeleteMe".equals(t.getText())).findFirst().orElse(null);
    assertThat(created).isNotNull();

    mockMvc.perform(MockMvcRequestBuilders.post("/delete_todo")
      .contentType(MediaType.APPLICATION_FORM_URLENCODED)
      .param("id", String.valueOf(created.getId())))
      .andExpect(status().is3xxRedirection()
    );

    mockMvc.perform(MockMvcRequestBuilders.get("/todos"))
      .andExpect(status().isOk())
      .andExpect(model().attribute("todos", org.hamcrest.Matchers.not(
        org.hamcrest.Matchers.hasItem(
          org.hamcrest.Matchers.hasProperty("text", org.hamcrest.Matchers.is("DeleteMe"))
        )
      ))
    );
  }
}