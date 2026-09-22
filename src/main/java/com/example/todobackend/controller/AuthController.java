package com.example.todobackend.controller;
import com.example.todobackend.entity.User;
import com.example.todobackend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.example.todobackend.dto.LoginRequest;
import org.springframework.security.core.AuthenticationException;
import com.example.todobackend.dto.LoginResponse;
import com.example.todobackend.security.CustomUserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/auth")

public class AuthController {
    private final UserService userService;
    private final SecurityContextRepository securityContextRepository;

    public AuthController(UserService userService, SecurityContextRepository securityContextRepository){
        this.userService=userService;
        this.securityContextRepository = securityContextRepository;
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {

        try{User savedUser = userService.registerUser(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedUser);}
        catch(RuntimeException e){
            return ResponseEntity
                    .status(HttpStatusCode.valueOf(409))
                    .body("Email already exists");
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest
    , HttpServletRequest request,
                                   HttpServletResponse response2) {
      try{
        Authentication authentication = userService.loginUser(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        securityContextRepository.saveContext(SecurityContextHolder.getContext(),
                request,response2);
        LoginResponse response=new LoginResponse(
                ((CustomUserDetails) authentication.getPrincipal()).getUser().getId(),
                ((CustomUserDetails) authentication.getPrincipal()).getUser().getName(),
                ((CustomUserDetails) authentication.getPrincipal()).getUser().getEmail()
                );
        return ResponseEntity.ok(response);
    }catch(AuthenticationException e){
          return ResponseEntity
                  .status(HttpStatus.UNAUTHORIZED)
                  .body("Invalid email or password");
      }

}

}
