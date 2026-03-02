package com.todo.todolistservice.repository;

import com.todo.todolistservice.model.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<TodoItem, Long> {
    List<TodoItem> findByUserId(String userId);

    List<TodoItem> findByUserIdAndCompleted(String userId, Boolean completed);

}
