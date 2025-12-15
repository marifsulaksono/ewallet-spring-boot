package com.marifsulaksono.ewallet.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/welcome")
public class WelcomeController {

    @GetMapping
    public String welcome() {
        return "Welcome to my code!";
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello World from Spring Boot!";
    }
}
