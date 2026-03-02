package com.todo.todolistservice.dto;

import com.todo.todolistservice.model.TodoItem;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TodoResponse {
    private Long id;
    private String userId;
    private String title;
    private String description;
    private Boolean completed;
    private TodoItem.TodoPriority priority;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
