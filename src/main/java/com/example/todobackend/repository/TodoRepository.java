package com.example.todobackend.repository;

import com.example.todobackend.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.todobackend.entity.User;
import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByDueDate(String dueDate);
    List<Todo> findByUser(User user);
    Optional<Todo> findByIdAndUser_Id(Long id,Long User_Id);

    List<Todo> findByDueDateAndUser(String dueDate, Optional<User> user);
    List<Todo> findByDueDateAndDoneFalse(String dueDate);
}
