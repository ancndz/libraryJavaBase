package ru.ancndz.libraryBase.configs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ancndz.libraryBase.configs.services.BookService;
import ru.ancndz.libraryBase.configs.services.LibraryService;
import ru.ancndz.libraryBase.content.libraryEnvironment.Book;
import ru.ancndz.libraryBase.content.libraryEnvironment.Library;

import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("/libs")
public class LibraryController {

    private final LibraryService libraryService;
    private final BookService bookService;

    @Autowired
    LibraryController(LibraryService libraryService, BookService bookService) {
        this.libraryService = libraryService;
        this.bookService = bookService;
    }

    @GetMapping("")
    public String home(Model model) {
        List<Library> libraryList = this.libraryService.libraryListBusiness();
        if (!libraryList.isEmpty()) {
            model.addAttribute("libraries", libraryList);
        }
        return "/libs/libraries";
    }

    @GetMapping("/new")
    public String newLibForm(Library library) {
        return "/libs/add_lib";
    }

    @PostMapping("/save")
    public String saveLib(@Valid Library library, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "/libs/add_lib";
        }
        this.libraryService.save(library);
        model.addAttribute("libraries", this.libraryService.libraryList());
        return "redirect:/libs/";
    }

    @GetMapping("/edit")
    public String editLibForm(@RequestParam Integer id, Model model) {
        Library library = this.libraryService.get(id);
        model.addAttribute("library", library);
        return "/libs/edit_lib";
    }

    @PostMapping("/delete")
    public String deleteLib(@RequestParam Integer id) {
        libraryService.delete(id);
        return "redirect:/libs/";
    }

    @GetMapping("/shelves")
    public String showShelves(@RequestParam Integer id, Model model) {
        List<Book> allLibBooks = this.bookService.booksByLibrary(id);
        MultiValueMap<String, Book> booksOnShelves = new LinkedMultiValueMap<>();
        String letter;
        for (Book book: allLibBooks) {
            letter = getTitleFirstLetter(book);
            booksOnShelves.add(letter, book);
        }
        Object[] sortedLetters = booksOnShelves.keySet().toArray();
        Arrays.sort(sortedLetters);
        model.addAttribute("letters", sortedLetters);
        model.addAttribute("shelf", booksOnShelves);
        model.addAttribute("lib_id", id);
        return "/libs/shelves";
    }

    private String getTitleFirstLetter(Book book) {
        String letter = book.getName();
        letter = letter.toLowerCase().replaceFirst("the ", "");
        letter = letter.substring(0, 1).toUpperCase();
        return letter;
    }
}


