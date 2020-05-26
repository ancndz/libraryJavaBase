package ru.ancndz.libraryBase.configs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ancndz.libraryBase.configs.services.*;
import ru.ancndz.libraryBase.content.entity.User;
import ru.ancndz.libraryBase.content.entity.UserExtras;
import ru.ancndz.libraryBase.content.libraryEnvironment.Book;
import ru.ancndz.libraryBase.content.libraryEnvironment.Staff;
import ru.ancndz.libraryBase.content.operations.Rent;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/rents")
public class RentController {
    private final RentService rentService;

    private final LoginService loginService;

    private final BookService bookService;
    private final LibraryService libraryService;

    @Autowired
    public RentController(RentService rentService, LoginService loginService, BookService bookService, LibraryService libraryService) {
        this.rentService = rentService;
        this.loginService = loginService;
        this.bookService = bookService;
        this.libraryService = libraryService;
    }

    @GetMapping("")
    public String home(Model model, Authentication authentication) {
        List<Rent> rents = new ArrayList<>();
        String userEmail;
        if (authentication != null) {
            if (authentication.isAuthenticated()) {
                if (authentication.getPrincipal() instanceof UserDetails) {
                    userEmail = ((UserDetails)authentication.getPrincipal()).getUsername();
                } else {
                    userEmail = authentication.getPrincipal().toString();
                }
                UserDetails userDetails = this.loginService.loadUserByUsername(userEmail);
                if (userDetails != null) {
                    if (userDetails instanceof User) {
                        rents = this.rentService.getAllByUserId(((User) userDetails).getId());
                    } else if (userDetails instanceof Staff) {
                        rents = this.rentService.rentList();
                    }
                }
            }
        }
        if (!rents.isEmpty()) {
            model.addAttribute("rents", rents);
        }
        return "/rents/rents";
    }

    @GetMapping("/new_order")
    public String newRentForm(@RequestParam int book_id, Model model, Authentication authentication) {
        if (authentication != null) {
            if (authentication.isAuthenticated()) {
                String userEmail;
                Rent rent = new Rent();
                if (authentication.getPrincipal() instanceof UserDetails) {
                    userEmail = ((UserDetails)authentication.getPrincipal()).getUsername();
                } else {
                    userEmail = authentication.getPrincipal().toString();
                }
                System.out.println("user email: " + userEmail);
                UserDetails userDetails = this.loginService.loadUserByUsername(userEmail);
                if (userDetails instanceof User) {
                    System.out.println("u a user");
                    rent.setUser((User) userDetails);
                    rent.setStaff((Staff)this.loginService.loadUserByUsername("email"));
                } else if (userDetails instanceof Staff) {
                    System.out.println(userDetails);
                    rent.setStaff((Staff) userDetails);
                    rent.setUser(new User());
                }
                rent.setBook(this.bookService.get(book_id));
                model.addAttribute("rent", rent);
                return "/rents/new_rent";
            }
        }
        model.addAttribute("error", "not authed");
        return "/rents/rents";
    }

    @PostMapping("/save")
    public String save(@Valid Rent rent, BindingResult result, Model model) {
        System.out.println(rent.getUser().getEmail());
        UserDetails userDetails = this.loginService.loadUserByUsername(rent.getUser().getEmail());
        if (userDetails != null) {
            rent.setUser((User)userDetails);
            rent.setStartDate(LocalDateTime.now());
            rent.setEndDate(LocalDateTime.now().plusMonths(1));
            Book book = this.bookService.get(rent.getBook().getId());
            if (book.getCount() - 1 >= 0) {
                System.out.println("books is in");
                book.setCount(book.getCount() - 1);
                this.bookService.save(book);
                rent.setBook(this.bookService.get(rent.getBook().getId()));
            } else {
                model.addAttribute("error", "Книг не осталось!");
                return "/rents/rents";
            }
            if (this.rentService.save(rent)) {
                return "redirect:/books/";
            } else {
                model.addAttribute("error", "У вас есть задолженности, невозможно совершить аренду.");
                return "/rents/rents";
            }
        } else {
            model.addAttribute("error", "Пользователь не найден!");
            return "/rents/rents";
        }
    }

    @GetMapping("/close")
    public String close(@RequestParam int id, Model model) {
        this.rentService.close(id);
        Book book = this.rentService.get(id).getBook();
        book.setCount(book.getCount() + 1);
        this.bookService.save(book);
        return "redirect:/rents/";
    }
}
