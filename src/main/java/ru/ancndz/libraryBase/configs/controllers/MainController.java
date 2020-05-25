package ru.ancndz.libraryBase.configs.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class MainController {

    @GetMapping("")
    public String mainPage() {
        return "/mainPage";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "/login";
    }
}
