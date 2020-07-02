package ru.ancndz.libraryBase.configs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ancndz.libraryBase.configs.services.BookService;
import ru.ancndz.libraryBase.configs.services.LoginService;
import ru.ancndz.libraryBase.configs.services.PenaltyService;
import ru.ancndz.libraryBase.configs.services.RentService;
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

    @Autowired
    public RentController(RentService rentService, PenaltyService penaltyService, LoginService loginService, BookService bookService) {
        this.rentService = rentService;
        this.penaltyService = penaltyService;
        this.loginService = loginService;
        this.bookService = bookService;
    }

    @GetMapping("")
    public String home(Model model, Authentication authentication) {
        List<Rent> rents = new ArrayList<>();
        List<Penalty> penalties = new ArrayList<>();
        if (authentication != null) {
            if (authentication.isAuthenticated()) {
                LibraryUser userDetails = this.loginService.loadByAuth(authentication);
                if (userDetails != null) {
                    if (userDetails instanceof Staff) {
                        rents = this.rentService.rentList();
                        penalties = this.penaltyService.penaltyList();
                    } else {
                        rents = this.rentService.getAllByUserId(userDetails.getId());
                        penalties = this.penaltyService.getAllByUserId(userDetails.getId());
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
                Rent rent = new Rent();
                LibraryUser userDetails = this.loginService.loadByAuth(authentication);
                if (userDetails instanceof Staff) {
                    rent.setStaff((Staff) userDetails);
                    rent.setLibraryUser(new LibraryUser());
                } else if (userDetails != null) {
                    rent.setLibraryUser(userDetails);
                    rent.setStaff((Staff) this.loginService.loadUserByUsername("email"));
                }
                rent.setBook(this.bookService.get(book_id));
                model.addAttribute("rent", rent);
                return "rents/new_rent";
            }
        }
        model.addAttribute("error", "Для аренды нужно представиться!");
        return "rents/rents";
    }

    @PostMapping("/save")
    public String save(@Valid Rent rent, BindingResult result, Model model) throws UsernameNotFoundException {
        UserDetails userDetails = this.loginService.loadUserByUsername(rent.getLibraryUser().getEmail());
        if (userDetails != null) {
            rent.setLibraryUser((LibraryUser) userDetails);
            rent.setStartDate(LocalDateTime.now());
            //rent.setEndDate(LocalDateTime.now().plusMonths(1));
            //todo change to 1 month
            rent.setEndDate(LocalDateTime.now().plusSeconds(15));
            Book book = this.bookService.get(rent.getBook().getId());
            if (book.getCount() - 1 >= 0) {
                rent.setBook(book);
            } else {
                model.addAttribute("error", "Книг не осталось!");
                return "rents/rents";
            }
            if (this.rentService.save(rent)) {
                book.setCount(book.getCount() - 1);
                this.bookService.save(book);
                return "redirect:/books/";
            } else {
                model.addAttribute("error", "У вас есть задолженности, невозможно совершить аренду.");
                return "rents/rents";
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

    @GetMapping("/add_penalty")
    public String addPenalty(@RequestParam int id, Model model) {
        Penalty penalty = new Penalty();
        penalty.setRent(this.rentService.get(id));
        penalty.setDate(LocalDateTime.now());
        penalty.setCompleteAmount(0);
        model.addAttribute("penalty", penalty);
        return "rents/new_penalty";
    }

    @PostMapping("/add_penalty")
    public String saveNewPenalty(@Valid Penalty penalty, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", bindingResult.getAllErrors());
            return "rents/new_penalty";
        }
        this.penaltyService.save(penalty);
        return "redirect:/rents/";
    }

    @GetMapping("/pay_penalty")
    public String pay(@RequestParam int id, Model model) {
        Penalty penalty = this.penaltyService.get(id);
        model.addAttribute("penalty", penalty);
        return "rents/pay";
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

        /*for (Rent each: libRents) {
            String book_letter = each.getBook().getName();
            book_letter = book_letter.toLowerCase().replaceFirst("the ", "");
            book_letter = book_letter.substring(0, 1).toLowerCase();

            if (letter.toLowerCase().equals(book_letter)) {
                activeBooks.add(each.getBook());
            }
        }*/

        libRents.stream()
                .filter(rent -> rent.getBook()
                        .getName().toLowerCase()
                        .replaceFirst("the", "").substring(0, 1)
                        .equals(letter))
                .forEach(rent -> activeBooks.add(rent.getBook()));

        model.addAttribute("books", activeBooks);
        model.addAttribute("letter", letter);
        return "rents/actives";
    }
}
