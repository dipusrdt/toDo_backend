package com.example.todobackend.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/api/admin/test")
    public String adminTest() {
        return "Welcome Admin";
    }
}