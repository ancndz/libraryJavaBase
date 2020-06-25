package ru.ancndz.libraryBase.configs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.ancndz.libraryBase.configs.services.LoginService;
import ru.ancndz.libraryBase.content.entity.LibraryUser;
import ru.ancndz.libraryBase.content.entity.UserExtras;

@Controller
public class MainController {

    private final LoginService loginService;

    @Autowired
    public MainController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("")
    public String mainPage(Authentication authentication, Model model) {
        String username = "Stranger";

        LibraryUser libraryUser = this.loginService.loadByAuth(authentication);

        if (libraryUser != null) {
            UserExtras extras = libraryUser.getUserExtras();
            username = extras.getFirstName() + " " + extras.getLastName();
        }
        model.addAttribute("name", username);
        //System.out.println(username);
        return "mainPage";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}
