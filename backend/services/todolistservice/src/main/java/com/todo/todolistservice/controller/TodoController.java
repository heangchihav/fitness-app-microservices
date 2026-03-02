package com.todo.todolistservice.controller;

import com.todo.todolistservice.dto.TodoRequest;
import com.todo.todolistservice.dto.TodoResponse;
import com.todo.todolistservice.service.TodoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@AllArgsConstructor
public class TodoController {

    private TodoService todoService;

    @PostMapping
    public ResponseEntity<TodoResponse> createTodo(@RequestBody TodoRequest request,
            @RequestHeader("X-User-ID") String userId) {
        return ResponseEntity.ok(todoService.createTodo(request, userId));
    }

    @GetMapping
    public ResponseEntity<List<TodoResponse>> getUserTodos(@RequestHeader("X-User-ID") String userId) {
        return ResponseEntity.ok(todoService.getUserTodos(userId));
    }

    @GetMapping("/{todoId}")
    public ResponseEntity<TodoResponse> getTodo(@PathVariable Long todoId, @RequestHeader("X-User-ID") String userId) {
        return ResponseEntity.ok(todoService.getTodoById(todoId, userId));
    }

    @PutMapping("/{todoId}")
    public ResponseEntity<TodoResponse> updateTodo(@PathVariable Long todoId, @RequestBody TodoRequest request,
            @RequestHeader("X-User-ID") String userId) {
        return ResponseEntity.ok(todoService.updateTodo(todoId, request, userId));
    }

    @DeleteMapping("/{todoId}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long todoId, @RequestHeader("X-User-ID") String userId) {
        todoService.deleteTodo(todoId, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status/{completed}")
    public ResponseEntity<List<TodoResponse>> getTodosByStatus(@PathVariable Boolean completed,
            @RequestHeader("X-User-ID") String userId) {
        return ResponseEntity.ok(todoService.getTodosByStatus(userId, completed));
    }
}
