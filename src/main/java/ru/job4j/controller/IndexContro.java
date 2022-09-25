package ru.job4j.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@ThreadSafe
@Controller
class IndexControl {

    @GetMapping("/index")
    public String index() {
        return "index";
    }
}
