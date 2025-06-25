package com.example.app.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.app.todo.model.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {}
