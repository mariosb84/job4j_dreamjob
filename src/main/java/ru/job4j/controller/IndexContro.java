package ru.job4j.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.utilites.Session;

import javax.servlet.http.HttpSession;

@ThreadSafe
@Controller
class IndexControl {

    @GetMapping("/index")
    public String index(Model model, HttpSession session) {
        Session.userSession(model, session);
        return "index";
    }

}
