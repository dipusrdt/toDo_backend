package com.example.todobackend.controller;

import com.example.todobackend.dto.DueTodoResponse;
import com.example.todobackend.service.TodoService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public class InternalTodoController {

    private final TodoService todoService;

    public InternalTodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/api/internal/todos/due")
    public List<DueTodoResponse> getDueTodos(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return todoService.getDueTodosForReminder(date.toString());
    }
}