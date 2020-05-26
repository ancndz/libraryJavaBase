package ru.ancndz.libraryBase.configs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.ancndz.libraryBase.configs.services.*;
import ru.ancndz.libraryBase.content.operations.Rent;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/rents")
public class RentController {
    private final RentService rentService;
    private final RoleService roleService;

    private final UserService userService;

    private final BookService bookService;
    private final LibraryService libraryService;

    @Autowired
    public RentController(RentService rentService, RoleService roleService, UserService userService, BookService bookService, LibraryService libraryService) {
        this.rentService = rentService;
        this.roleService = roleService;
        this.userService = userService;
        this.bookService = bookService;
        this.libraryService = libraryService;
    }

    @GetMapping("")
    public String home(Model model, Authentication authentication) {
        List<Rent> rents = new ArrayList<>();
        if (authentication.isAuthenticated()) {
            if (authentication.getAuthorities().contains(this.roleService.getRoleByName("ADMIN"))) {
                rents = this.rentService.rentList();
            } else {
                rents = this.rentService.getByUserId(
                        this.userService.getByEmail(authentication.getPrincipal().toString()).getId());
            }
        }
        if (!rents.isEmpty()) {
            model.addAttribute("rents", rents);
        }
        return "/rents/rents";
    }

    @GetMapping("/new")
    public String newRentForm(Model model, Rent rent) {
        return "/rents/new_rent";
    }
}
