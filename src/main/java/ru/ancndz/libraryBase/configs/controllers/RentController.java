package ru.ancndz.libraryBase.configs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.ancndz.libraryBase.configs.services.BookService;
import ru.ancndz.libraryBase.configs.services.LibraryService;
import ru.ancndz.libraryBase.configs.services.RentService;
import ru.ancndz.libraryBase.content.operations.Rent;

import java.util.List;

@Controller
@RequestMapping("/rents")
public class RentController {
    private final RentService rentService;

    private final BookService bookService;
    private final LibraryService libraryService;

    @Autowired
    public RentController(RentService rentService, BookService bookService, LibraryService libraryService) {
        this.rentService = rentService;
        this.bookService = bookService;
        this.libraryService = libraryService;
    }

    @GetMapping("")
    public String home(Model model) {
        List<Rent> rents = this.rentService.rentList();
        if (!rents.isEmpty()) {
            model.addAttribute("rents", rents);
        }
        return "rents";
    }

    @GetMapping("/new")
    public String newRentForm(Model model, Rent rent) {
        return "new_rent";
    }
}
