package com.example.todobackend.service;

import com.example.todobackend.entity.Todo;
import com.example.todobackend.entity.User;
import com.example.todobackend.repository.TodoRepository;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import com.example.todobackend.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class  TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    public TodoService(TodoRepository todoRepository,
                       UserRepository userRepository) {
        this.todoRepository = todoRepository;
        this.userRepository = userRepository;

    }
    public Todo saveTodo(Todo todo,Long userId) {


            User user = userRepository.findById(userId)
                    .orElseThrow();

            todo.setUser(user);

        todo.setDate(String.valueOf(LocalDate.now()));
        return todoRepository.save(todo);
    }
    public List<Todo> getAllTodos(){
        return todoRepository.findAll();
    }
    public List<Todo> getTodosByUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow();

        return todoRepository.findByUser(user);
    }

    public List<Todo> getTodosByDateandId(String dueDate,Long id){
        Optional<User> user=userRepository.findById(id);
        return todoRepository.findByDueDateAndUser(dueDate, user);
    }
    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }
}