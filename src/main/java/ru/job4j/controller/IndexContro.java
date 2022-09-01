package ru.job4j.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class IndexControl {

    @GetMapping("/index")
    public String index() {
        return "Greetings from Spring Boot! Greetings from Spring Boot! Greetings from Spring Boot!";
    }
}
