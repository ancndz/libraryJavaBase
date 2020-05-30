package ru.ancndz.libraryBase.configs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ancndz.libraryBase.configs.services.BookService;
import ru.ancndz.libraryBase.configs.services.LibraryService;
import ru.ancndz.libraryBase.content.libraryEnvironment.Book;
import ru.ancndz.libraryBase.content.libraryEnvironment.Library;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/books")
public class CatalogController {

    private final BookService bookService;
    private final LibraryService libraryService;

    @Autowired
    CatalogController(BookService bookService, LibraryService libraryService) {
        this.bookService = bookService;
        this.libraryService = libraryService;
    }

    @GetMapping("")
    public String home(Model model) {
        List<Book> bookList = this.bookService.booksList();
        if (!bookList.isEmpty()) {
            model.addAttribute("bookList", bookList);
        }
        return "/books/catalog";
    }

    /*@GetMapping("/filter")
    public String homeByLibId(@RequestParam(value = "id") int id, Model model) {
        List<Book> bookList = bookService.booksByLibrary(id);
        if (!bookList.isEmpty()) {
            model.addAttribute("bookList", bookList);
        }
        return "/books/catalog";
    }*/

    @GetMapping("/filter")
    public String homeByAuthor(@RequestParam Map<String,String> allParams, Model model) {
        int year = 0;
        int lib_id = 0;
        String name = null;
        String author = null;
        String genre = null;
        if (allParams.containsKey("name")) {
            name = allParams.get("name");
        }
        if (allParams.containsKey("author")) {
            author = allParams.get("author");
        }
        if (allParams.containsKey("genre")) {
            genre = allParams.get("genre");
        }
        if (allParams.containsKey("year")) {
            try {
                year = Integer.getInteger(allParams.get("year"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (allParams.containsKey("lib_id")) {
            try {
                year = Integer.getInteger(allParams.get("lib_id"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        List<Book> bookList = bookService.findByCriteria(name,
                author, year, genre, lib_id);
        if (!bookList.isEmpty()) {
            model.addAttribute("bookList", bookList);
        }
        return "/books/catalog";
    }

    @GetMapping("/new")
    public String newBookForm(Model model, Book book) {
        List<Library> libraryList = this.libraryService.libraryList();
        model.addAttribute("libraries", libraryList);
        return "/books/add_book";
    }

    @PostMapping("/save")
    public String saveBook(@Valid Book book, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<Library> libraryList = this.libraryService.libraryList();
            model.addAttribute("libraries", libraryList);
            String error = result.toString();
            model.addAttribute("error", error);
            return "/books/add_book";
        }
        book.setLibrary(this.libraryService.get(book.getLibraryId()));
        this.bookService.save(book);
        //model.addAttribute("bookList", bookService.booksList());
        return "redirect:/books/";
    }

    @GetMapping("/edit")
    public String editBookForm(@RequestParam Integer id, Model model) {
        Book book = bookService.get(id);
        model.addAttribute("book", book);
        List<Library> libraryList = this.libraryService.libraryList();
        model.addAttribute("libraries", libraryList);
        return "/books/edit_book";
    }

    @PostMapping("/delete")
    public String deleteBook(@RequestParam Integer id) {
        bookService.delete(id);
        return "redirect:/books/";
    }

}


