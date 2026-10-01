package com.example.todobackend.service;

import com.example.todobackend.entity.Todo;
import com.example.todobackend.entity.User;
import com.example.todobackend.repository.TodoRepository;
import org.springframework.stereotype.Service;
import com.example.todobackend.repository.UserRepository;
import com.example.todobackend.dto.DueTodoResponse;

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
    public boolean deleteTodo(Long id,Long userId) {
        Optional<Todo> todo = todoRepository.findByIdAndUser_Id(id, userId);
        if (todo.isEmpty()) {
            return false;
        }
        todoRepository.delete(todo.get());
        return true;
    }
    public Optional<Todo> setDone(Long id,
                                  Long userId,
                                  boolean done){
        Optional <Todo> todo=todoRepository.findByIdAndUser_Id(id,userId);
        todo.ifPresent(t->{
            t.setDone(done);
            todoRepository.save(t);
        });
        return todo;
    }
    public List<DueTodoResponse> getDueTodosForReminder(String dueDate) {
        return todoRepository.findByDueDateAndDoneFalse(dueDate).stream()
                .filter(t -> t.getUser() != null && t.getUser().getEmail() != null)
                .map(t -> new DueTodoResponse(t.getId(), t.getTitle(), t.getDueDate(),
                        t.getUser().getEmail(), t.getUser().getName()))
                .toList();
    }
}