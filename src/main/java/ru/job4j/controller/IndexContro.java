package ru.job4j.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class IndexControl {

    @GetMapping("/index")
    public String index() {
        return "index";
    }
}
