package com.example.app.todo;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.example.app.todo.model.Todo;
import com.example.app.todo.repository.TodoRepository;

@Controller
public class TodoController {

  @Autowired
  private TodoRepository todoRepository;

  @GetMapping("/")
  public String redirectToTodos() {
    return "redirect:/todos";
  }

  @GetMapping("/todos")
  public String getTodos(Model model) {
    model.addAttribute("todos", todoRepository.findAll());
    return "todos";
  }

  @PostMapping("/todos")
  public String createTodo(@RequestParam String text) {
    todoRepository.save(new Todo(text));
    return "redirect:/todos";
  }

  @PostMapping("/delete_todo")
  public String deleteTodo(@RequestParam Long id) {
    // Obviously not safe, would need to add user log in/auth
    Todo todo = todoRepository.getReferenceById(id);
    todoRepository.delete(todo);
    return "redirect:/todos";
  }
}