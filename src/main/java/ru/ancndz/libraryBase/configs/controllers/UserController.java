package ru.ancndz.libraryBase.configs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ancndz.libraryBase.configs.services.RentService;
import ru.ancndz.libraryBase.configs.services.UserService;
import ru.ancndz.libraryBase.content.entity.User;
import ru.ancndz.libraryBase.content.operations.Rent;

import javax.jws.WebParam;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final RentService rentService;

    @Autowired
    UserController(UserService userService, RentService rentService) {
        this.userService = userService;
        this.rentService = rentService;
    }

    @GetMapping("")
    public String usersHome(Model model) {
        List<User> userList = userService.getAll();
        if (!userList.isEmpty()) {
            model.addAttribute("userList", userList);
        }
        model.addAttribute("listName", "Все участники");
        return "users/user";
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

    @GetMapping("/reading_now")
    public String readingNow(@RequestParam(value = "name") String name, @RequestParam(value = "author") String author, Model model) {
        List<Rent> rents = this.rentService.getAllActiveByBook(author, name);
        List<User> users = new ArrayList<>();
        rents.forEach(rent -> users.add(rent.getUser()));
        model.addAttribute("userList", users);
        model.addAttribute("listName", "Читатели " + author + ", " + name);
        return "users/user";
    }

    @GetMapping("/edit")
    public String editClientForm(@RequestParam Integer id, Model model) {
        User user = this.userService.get(id);
        model.addAttribute("user", user);
        return "users/edit_user";
    }

    @PostMapping("/delete")
    public String deleteClient(@RequestParam Integer id) {
        this.userService.delete(id);
        return "redirect:/users/";
    }

    @GetMapping("/lost")
    public String findLostClients(@RequestParam(value = "date") String dateString, Model model) {
        ChronoUnit chronoUnit;
        switch (dateString) {
            case "week": chronoUnit = ChronoUnit.WEEKS;
                break;
            case "month": chronoUnit = ChronoUnit.MONTHS;
                break;
            case "year": chronoUnit = ChronoUnit.YEARS;
                break;
            default: chronoUnit = ChronoUnit.MINUTES;
                break;
        }
        List<User> allUsers = this.userService.getAll();
        List<User> lostUser = new ArrayList<>();
        for (User user: allUsers) {
            Rent lastRent = this.rentService.getLastRentByUserId(user.getId());
            if (lastRent != null) {
                if (lastRent.getFactEndDate() != null &&
                        lastRent.getStartDate().plus(1, chronoUnit).isBefore(LocalDateTime.now())) {
                    lostUser.add(user);
                }
            } else {
                lostUser.add(user);
            }
        }
        model.addAttribute("userList", lostUser);
        model.addAttribute("listName", "Неактивные участники");
        return "users/user";
    }

}


