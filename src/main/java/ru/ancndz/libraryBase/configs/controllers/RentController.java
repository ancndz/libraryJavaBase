package ru.ancndz.libraryBase.configs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ancndz.libraryBase.configs.services.*;
import ru.ancndz.libraryBase.content.entity.LibraryUser;
import ru.ancndz.libraryBase.content.entity.Staff;
import ru.ancndz.libraryBase.content.libraryEnvironment.Book;
import ru.ancndz.libraryBase.content.operations.Penalty;
import ru.ancndz.libraryBase.content.operations.Rent;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/rents")
public class RentController {
    private final RentService rentService;
    private final PenaltyService penaltyService;

    private final LoginService loginService;

    private final BookService bookService;
    private final LibraryService libraryService;

    @Autowired
    public RentController(RentService rentService, PenaltyService penaltyService, LoginService loginService, BookService bookService, LibraryService libraryService) {
        this.rentService = rentService;
        this.penaltyService = penaltyService;
        this.loginService = loginService;
        this.bookService = bookService;
        this.libraryService = libraryService;
    }

    @GetMapping("")
    public String home(Model model, Authentication authentication) {
        List<Rent> rents = new ArrayList<>();
        List<Penalty> penalties = new ArrayList<>();
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
                    if (userDetails instanceof LibraryUser) {
                        rents = this.rentService.getAllByUserId(((LibraryUser) userDetails).getId());
                        penalties = this.penaltyService.getAllByUserId(((LibraryUser) userDetails).getId());
                    } else if (userDetails instanceof Staff) {
                        rents = this.rentService.rentList();
                        penalties = this.penaltyService.penaltyList();
                    }
                }
            }
        }
        if (!rents.isEmpty()) {
            model.addAttribute("rents", rents);
        }
        if (!penalties.isEmpty()) {
            model.addAttribute("penalties", penalties);
        }
        return "rents/rents";
    }

    @PostMapping("/new_order")
    public String newRentForm(@RequestParam int book_id, Model model, Authentication authentication) {
        if (authentication != null) {
            if (authentication.isAuthenticated()) {
                String userEmail;
                Rent rent = new Rent();
                if (authentication.getPrincipal() instanceof UserDetails) {
                    userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
                } else {
                    userEmail = authentication.getPrincipal().toString();
                }
                UserDetails userDetails = this.loginService.loadUserByUsername(userEmail);
                if (userDetails instanceof LibraryUser) {
                    rent.setLibraryUser((LibraryUser) userDetails);
                    rent.setStaff((Staff) this.loginService.loadUserByUsername("email"));
                } else if (userDetails instanceof Staff) {
                    System.out.println(userDetails);
                    rent.setStaff((Staff) userDetails);
                    rent.setLibraryUser(new LibraryUser());
                }
                rent.setBook(this.bookService.get(book_id));
                model.addAttribute("rent", rent);
                return "/rents/new_rent";
            }
        }
        model.addAttribute("error", "not authed");
        return "rents/rents";
    }

    @PostMapping("/save")
    public String save(@Valid Rent rent, BindingResult result, Model model) {
        UserDetails userDetails = this.loginService.loadUserByUsername(rent.getLibraryUser().getEmail());
        if (userDetails != null) {
            rent.setLibraryUser((LibraryUser) userDetails);
            rent.setStartDate(LocalDateTime.now());
            //rent.setEndDate(LocalDateTime.now().plusMonths(1));
            //todo change to 1 month
            rent.setEndDate(LocalDateTime.now().plusSeconds(5));
            Book book = this.bookService.get(rent.getBook().getId());
            if (book.getCount() - 1 >= 0) {
                rent.setBook(book);
            } else {
                model.addAttribute("error", "Книг не осталось!");
                return "/rents/rents";
            }
            if (this.rentService.save(rent)) {
                book.setCount(book.getCount() - 1);
                this.bookService.save(book);
                return "redirect:/books/";
            } else {
                model.addAttribute("error", "У вас есть задолженности, невозможно совершить аренду.");
                return "/rents/rents";
            }
        } else {
            model.addAttribute("error", "Пользователь не найден!");
            return "rents/rents";
        }
    }

    @GetMapping("/refresh")
    public String refresh() {
        this.rentService.findOutdatedRents();
        return "redirect:/rents/";
    }

    @PostMapping("/close")
    public String close(@RequestParam int id, Model model) {
        this.rentService.close(id);
        Book book = this.rentService.get(id).getBook();
        book.setCount(book.getCount() + 1);
        this.bookService.save(book);
        return "redirect:/rents/";
    }

    @GetMapping("/pay_penalty")
    public String pay(@RequestParam int id, Model model) {
        Penalty penalty = this.penaltyService.get(id);
        model.addAttribute("penalty", penalty);
        return  "rents/pay";
    }

    @PostMapping("/pay_penalty/save")
    public String savePay(@RequestParam(value = "pay") int pay, @RequestParam(value = "id") int id) {
        this.penaltyService.payPenalty(id, pay);
        return "redirect:/rents/";
    }

    @GetMapping("/active")
    public String viewActives(@RequestParam(value = "letter") String letter, @RequestParam(value = "lib_id") int id, Model model) {
        List<Rent> libRents = this.rentService.getActiveByLibId(id);
        List<Book> activeBooks = new ArrayList<>();
        for (Rent each: libRents) {
            String book_letter = each.getBook().getName();
            book_letter = book_letter.toLowerCase().replaceFirst("the ", "");
            book_letter = book_letter.substring(0, 1).toLowerCase();

            if (letter.toLowerCase().equals(book_letter)) {
                activeBooks.add(each.getBook());
            }
        }
        model.addAttribute("books", activeBooks);
        model.addAttribute("letter", letter);
        return "rents/actives";
    }
}
