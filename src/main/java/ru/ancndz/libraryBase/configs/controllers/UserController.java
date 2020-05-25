package ru.ancndz.libraryBase.configs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ancndz.libraryBase.configs.services.UserService;
import ru.ancndz.libraryBase.content.entity.User;
import ru.ancndz.libraryBase.content.entity.UserExtras;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String usersHome(Model model) {
        List<User> userList = userService.getAll();
        if (!userList.isEmpty()) {
            model.addAttribute("userList", userList);
        }
        return "/users/user";
    }

    @GetMapping("/new")
    public String newClientForm(User user) {
        /*user.setUserExtras(new UserExtras());
        user.getUserExtras().setDateReg(LocalDateTime.now());
        return "/users/add_user";*/
        return "redirect:/registration/";
    }

    @PostMapping("/save")
    public String saveClient(@Valid User user, BindingResult result, Model model) {
        /*if (result.hasErrors()) {
            String errorText = result.toString();
            model.addAttribute("errorText", errorText);
            return "/users/add_user";
        } else if (!user.passwordsCheck()) {
            String errorText = "Passwords not equals!";
            model.addAttribute("errorText", errorText);
            return "/users/add_user";
        }
        userService.save(user);
        return "redirect:/users/";*/
        return "redirect:/registration/save";
    }

    @GetMapping("/edit")
    public String editClientForm(@RequestParam Integer id, Model model) {
        User user = this.userService.get(id);
        model.addAttribute("user", user);
        return "/users/edit_user";
    }

    @GetMapping("/delete")
    public String deleteClient(@RequestParam Integer id) {
        this.userService.delete(id);
        return "redirect:/users/";
    }

}


