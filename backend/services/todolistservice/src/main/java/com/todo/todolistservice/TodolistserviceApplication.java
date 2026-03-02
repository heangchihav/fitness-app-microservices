package com.todo.todolistservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TodolistserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodolistserviceApplication.class, args);
	}

}
