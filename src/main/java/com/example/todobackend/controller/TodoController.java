package com.example.todobackend.controller;

import com.example.todobackend.entity.Todo;
import com.example.todobackend.entity.User;
import com.example.todobackend.service.TodoService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.example.todobackend.security.CustomUserDetails;

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
    public Todo createTodo(@RequestBody Todo todo) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();
        User user=userDetails.getUser();

        return todoService.saveTodo(todo,user.getId());
    }

    @GetMapping("/api/todos")
    public List<Todo> getAllTodos(@RequestParam Long userId) {
        return todoService.getTodosByUser(userId);
    }

    @GetMapping("/api/todos/{date}/due/{id}")
    public List<Todo> getTodosBydueDateandId(@PathVariable String date,@PathVariable Long id) {
        return todoService.getTodosByDateandId(date,id);
    }

    @DeleteMapping("/api/todos/{id}")
    public void deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
    }

    @GetMapping("/api/todos/user")
    public List<Todo> getTodosByUser() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();
        User user=userDetails.getUser();

        return todoService.getTodosByUser(user.getId());
    }
}
