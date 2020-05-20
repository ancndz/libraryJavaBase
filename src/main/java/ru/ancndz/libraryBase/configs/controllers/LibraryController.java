package ru.ancndz.libraryBase.configs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ancndz.libraryBase.configs.services.LibraryService;
import ru.ancndz.libraryBase.content.libraryEnvironment.Library;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/libs")
public class LibraryController {

    private final LibraryService libraryService;

    @Autowired
    LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping("")
    public String home(Model model) {
        List<Library> libraryList = this.libraryService.libraryList();
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

    @GetMapping("/edit-lib")
    public String editLibForm(@RequestParam Integer id, Model model) {
        Library library = this.libraryService.get(id);
        model.addAttribute("library", library);
        return "/libs/edit_lib";
    }

    @GetMapping("/delete")
    public String deleteLib(@RequestParam Integer id) {
        libraryService.delete(id);
        return "redirect:/libs/";
    }

}


