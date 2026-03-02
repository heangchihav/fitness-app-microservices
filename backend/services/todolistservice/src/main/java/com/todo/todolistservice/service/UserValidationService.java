package com.todo.todolistservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class UserValidationService {

    private final WebClient webClient;

    public UserValidationService(@Value("${userservice.url:http://userservice}") String userServiceUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(userServiceUrl)
                .build();
    }

    public boolean validateUser(String userId) {
        try {
            return webClient.get()
                    .uri("/api/users/" + userId + "/validate")
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();
        } catch (Exception e) {
            return false;
        }
    }
}
