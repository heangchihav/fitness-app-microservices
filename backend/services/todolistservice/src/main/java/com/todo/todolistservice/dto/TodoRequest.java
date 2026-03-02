package com.todo.todolistservice.dto;

import com.todo.todolistservice.model.TodoItem;
import lombok.Data;

@Data
public class TodoRequest {
    private String title;
    private String description;
    private Boolean completed;
    private TodoItem.TodoPriority priority;
}
