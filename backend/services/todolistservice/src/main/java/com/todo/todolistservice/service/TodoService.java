package com.todo.todolistservice.service;

import com.todo.todolistservice.dto.TodoRequest;
import com.todo.todolistservice.dto.TodoResponse;
import com.todo.todolistservice.model.TodoItem;
import com.todo.todolistservice.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TodoService {

    private final TodoRepository todoRepository;
    private final UserValidationService userValidationService;

    public TodoResponse createTodo(TodoRequest request, String userId) {
        boolean isValidUser = userValidationService.validateUser(userId);
        if (!isValidUser) {
            throw new RuntimeException("Invalid User: " + userId);
        }

        TodoItem todoItem = TodoItem.builder()
                .userId(userId)
                .title(request.getTitle())
                .description(request.getDescription())
                .completed(request.getCompleted() != null ? request.getCompleted() : false)
                .priority(request.getPriority() != null ? request.getPriority() : TodoItem.TodoPriority.MEDIUM)
                .build();

        TodoItem savedTodo = todoRepository.save(todoItem);
        log.info("Created todo item: {} for user: {}", savedTodo.getId(), userId);

        return mapToResponse(savedTodo);
    }

    public List<TodoResponse> getUserTodos(String userId) {
        boolean isValidUser = userValidationService.validateUser(userId);
        if (!isValidUser) {
            throw new RuntimeException("Invalid User: " + userId);
        }

        List<TodoItem> todos = todoRepository.findByUserId(userId);
        return todos.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public TodoResponse getTodoById(Long todoId, String userId) {
        boolean isValidUser = userValidationService.validateUser(userId);
        if (!isValidUser) {
            throw new RuntimeException("Invalid User: " + userId);
        }

        return todoRepository.findById(todoId)
                .filter(todo -> todo.getUserId().equals(userId))
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Todo not found with id: " + todoId));
    }

    public TodoResponse updateTodo(Long todoId, TodoRequest request, String userId) {
        boolean isValidUser = userValidationService.validateUser(userId);
        if (!isValidUser) {
            throw new RuntimeException("Invalid User: " + userId);
        }

        TodoItem existingTodo = todoRepository.findById(todoId)
                .filter(todo -> todo.getUserId().equals(userId))
                .orElseThrow(() -> new RuntimeException("Todo not found with id: " + todoId));

        existingTodo.setTitle(request.getTitle());
        existingTodo.setDescription(request.getDescription());
        existingTodo
                .setCompleted(request.getCompleted() != null ? request.getCompleted() : existingTodo.getCompleted());
        existingTodo.setPriority(request.getPriority() != null ? request.getPriority() : existingTodo.getPriority());

        TodoItem updatedTodo = todoRepository.save(existingTodo);
        log.info("Updated todo item: {} for user: {}", updatedTodo.getId(), userId);

        return mapToResponse(updatedTodo);
    }

    public void deleteTodo(Long todoId, String userId) {
        boolean isValidUser = userValidationService.validateUser(userId);
        if (!isValidUser) {
            throw new RuntimeException("Invalid User: " + userId);
        }

        todoRepository.findById(todoId)
                .filter(todo -> todo.getUserId().equals(userId))
                .orElseThrow(() -> new RuntimeException("Todo not found with id: " + todoId));

        todoRepository.deleteById(todoId);
        log.info("Deleted todo item: {} for user: {}", todoId, userId);
    }

    public List<TodoResponse> getTodosByStatus(String userId, Boolean completed) {
        boolean isValidUser = userValidationService.validateUser(userId);
        if (!isValidUser) {
            throw new RuntimeException("Invalid User: " + userId);
        }

        List<TodoItem> todos = todoRepository.findByUserIdAndCompleted(userId, completed);
        return todos.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private TodoResponse mapToResponse(TodoItem todoItem) {
        TodoResponse response = new TodoResponse();
        response.setId(todoItem.getId());
        response.setUserId(todoItem.getUserId());
        response.setTitle(todoItem.getTitle());
        response.setDescription(todoItem.getDescription());
        response.setCompleted(todoItem.getCompleted());
        response.setPriority(todoItem.getPriority());
        response.setCreatedAt(todoItem.getCreatedAt());
        response.setUpdatedAt(todoItem.getUpdatedAt());
        return response;
    }
}
