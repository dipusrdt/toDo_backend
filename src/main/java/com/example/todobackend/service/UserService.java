package com.example.todobackend.service;
import com.example.todobackend.entity.User;
import com.example.todobackend.repository.UserRepository;
import org.springframework.cglib.core.Local;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;



import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository ,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager){
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
        this.authenticationManager=authenticationManager;
    }
    public  User registerUser(User user){
        Optional<User> existingUser=userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()){
            throw new RuntimeException("Email already registered");

        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    public Authentication loginUser(String email, String password) {

        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        email,password
                )
        );
        return authentication;
        }
}

