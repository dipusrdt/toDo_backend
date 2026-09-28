package com.example.todobackend.controller;

import com.example.todobackend.entity.Todo;
import com.example.todobackend.service.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/api/hello")
    public String hello() {
        return "Todo API is working!";
    }

    @PostMapping("/api/todos")
    public Todo createTodo(@RequestBody Todo todo, @AuthenticationPrincipal Jwt jwt) {
        Long userId = jwt.getClaim("id");
        return todoService.saveTodo(todo, userId);
    }

    @GetMapping("/api/todos")
    public List<Todo> getAllTodos(@AuthenticationPrincipal Jwt jwt) {
        Long userId = jwt.getClaim("id");
        return todoService.getTodosByUser(userId);
    }

    @GetMapping("/api/todos/{date}/due")
    public List<Todo> getTodosBydueDateandId(@PathVariable String date, @AuthenticationPrincipal Jwt jwt) {
        Long userId = jwt.getClaim("id");
        return todoService.getTodosByDateandId(date, userId);
    }

    @GetMapping("/api/todos/user")
    public List<Todo> getTodosByUser(@AuthenticationPrincipal Jwt jwt) {
        Long userId = jwt.getClaim("id");
        return todoService.getTodosByUser(userId);
    }

    @DeleteMapping("/api/todos/{id}")
    public ResponseEntity<Void> deleteTodos(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        Long userId = jwt.getClaim("id");
        boolean deleted = todoService.deleteTodo(id, userId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}