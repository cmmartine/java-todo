package com.example.app.todo;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.example.app.todo.model.Todo;
import com.example.app.todo.repository.TodoRepository;

@RestController
public class TodoController {

  @Autowired
  private TodoRepository todoRepository;

  @GetMapping("/api/todos")
  public List<Todo> getTodos() {
    return todoRepository.findAll();
  }

  @PostMapping("/api/todos")
  public Todo createTodo(@RequestBody Todo todo) {
    return todoRepository.save(todo);
  }
}