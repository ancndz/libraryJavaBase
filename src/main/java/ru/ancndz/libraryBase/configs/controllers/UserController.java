package ru.ancndz.libraryBase.configs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ancndz.libraryBase.configs.services.RentService;
import ru.ancndz.libraryBase.configs.services.UserService;
import ru.ancndz.libraryBase.content.entity.LibraryUser;
import ru.ancndz.libraryBase.content.operations.Rent;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        List<LibraryUser> libraryUserList = userService.getAll();
        if (!libraryUserList.isEmpty()) {
            model.addAttribute("libraryUserList", libraryUserList);
        }
        model.addAttribute("listName", "Все участники");
        return "users/user";
    }

    @GetMapping("/new")
    public String newClientForm(LibraryUser libraryUser) {
        /*user.setUserExtras(new UserExtras());
        user.getUserExtras().setDateReg(LocalDateTime.now());
        return "/users/add_user";*/
        return "redirect:/registration/";
    }

    @PostMapping("/save")
    public String saveClient(@Valid LibraryUser libraryUser, BindingResult result, Model model) {
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
        List<LibraryUser> libraryUsers = new ArrayList<>();
        rents.forEach(rent -> libraryUsers.add(rent.getLibraryUser()));
        model.addAttribute("libraryUserList", libraryUsers);
        model.addAttribute("listName", "Читатели " + author + ", " + name);
        return "users/user";
    }

    @GetMapping("/edit")
    public String editClientForm(@RequestParam Integer id, Model model) {
        LibraryUser libraryUser = this.userService.get(id);
        model.addAttribute("userLibrary", libraryUser);
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
        String chronoString;
        switch (dateString) {
            case "week":
                chronoUnit = ChronoUnit.WEEKS;
                chronoString = "неделя";
                break;
            case "month":
                chronoUnit = ChronoUnit.MONTHS;
                chronoString = "месяц";
                break;
            case "year":
                chronoUnit = ChronoUnit.YEARS;
                chronoString = "год";
                break;
            default:
                chronoUnit = ChronoUnit.MINUTES;
                chronoString = "минута";
                break;
        }

        List<LibraryUser> lostLibraryUser = this.userService
                .getAll()
                .stream()
                .filter(user -> checkLostUser(user, chronoUnit))
                .collect(Collectors.toList());

        model.addAttribute("libraryUserList", lostLibraryUser);
        model.addAttribute("listName", "Неактивные участники, " + chronoString);
        return "users/user";
    }

    /**
     * @param user       checking user
     * @param chronoUnit time period
     * @return true if client lost
     */
    private boolean checkLostUser(LibraryUser user, ChronoUnit chronoUnit) {
        Rent lastRent = this.rentService.getLastRentByUserId(user.getId());
        if (lastRent != null) {
            return lastRent.getFactEndDate() != null &&
                    lastRent.getStartDate().plus(1, chronoUnit).isBefore(LocalDateTime.now());
        } else {
            return true;
        }
    }

}


