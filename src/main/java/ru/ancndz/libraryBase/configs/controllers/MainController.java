package ru.ancndz.libraryBase.configs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.ancndz.libraryBase.configs.services.LoginService;
import ru.ancndz.libraryBase.configs.services.UserService;
import ru.ancndz.libraryBase.content.entity.User;
import ru.ancndz.libraryBase.content.entity.UserExtras;
import ru.ancndz.libraryBase.content.libraryEnvironment.Staff;

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
        if (authentication != null) {
            if (authentication.isAuthenticated()) {
                if (authentication.getPrincipal() instanceof UserDetails) {
                    username = ((UserDetails)authentication.getPrincipal()).getUsername();
                } else {
                    username = authentication.getPrincipal().toString();
                }
                UserDetails userDetails = this.loginService.loadUserByUsername(username);
                if (userDetails instanceof User) {
                    UserExtras extras = ((User) userDetails).getUserExtras();
                    username = extras.getFirstName() + " " + extras.getLastName();
                } else if (userDetails instanceof Staff) {
                    username = ((Staff) userDetails).getFirstName() + " " + ((Staff) userDetails).getLastName();
                }

            }
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
