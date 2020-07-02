package ru.ancndz.libraryBase.configs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ancndz.libraryBase.configs.services.BookService;
import ru.ancndz.libraryBase.configs.services.LibraryService;
import ru.ancndz.libraryBase.content.libraryEnvironment.Book;
import ru.ancndz.libraryBase.content.libraryEnvironment.Library;

import javax.validation.Valid;
import java.util.List;

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
            model.addAttribute("listName", "All books");
        }
        return "books/catalog";
    }


    @GetMapping("/filter")
    public String homeByAuthor(@RequestParam(value = "name", required = false) String name,
                               @RequestParam(value = "author", required = false) String author,
                               @RequestParam(value = "genre", required = false) String genre,
                               @RequestParam(value = "edition", required = false) String edition,
                               @RequestParam(value = "year", required = false) String year,
                               @RequestParam(value = "lib_id", required = false) String lib_id,
                               Model model) {
        int year_int = 0;
        int lib_id_int = 0;
        try {
            year_int = Integer.parseInt(year);
        } catch (Exception ignored) {

        }
        try {
            lib_id_int =Integer.parseInt(lib_id);
        } catch (Exception ignored) {

        }
        List<Book> bookList = bookService.findByCriteria(name,
                author, year_int, genre, edition, lib_id_int);
        if (!bookList.isEmpty()) {
            model.addAttribute("bookList", bookList);
        }
        return "books/catalog";
    }

    @GetMapping("/new")
    public String newBookForm(Model model, Book book) {
        List<Library> libraryList = this.libraryService.libraryList();
        model.addAttribute("libraries", libraryList);
        return "books/add_book";
    }

    @PostMapping("/save")
    public String saveBook(@Valid Book book, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<Library> libraryList = this.libraryService.libraryList();
            model.addAttribute("libraries", libraryList);
            String error = result.toString();
            model.addAttribute("error", error);
            return "books/add_book";
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
        return "books/edit_book";
    }

    @PostMapping("/delete")
    public String deleteBook(@RequestParam Integer id) {
        bookService.delete(id);
        return "redirect:/books/";
    }

}


