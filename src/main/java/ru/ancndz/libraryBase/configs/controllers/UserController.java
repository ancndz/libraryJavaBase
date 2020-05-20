package ru.ancndz.libraryBase.configs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ancndz.libraryBase.configs.services.UserService;
import ru.ancndz.libraryBase.content.entity.User;
import ru.ancndz.libraryBase.content.libraryEnvironment.Card;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String usersHome(Model model) {
        List<User> userList = userService.clientList();
        //String debugString = "";
        if (!userList.isEmpty()) {
            //debugString = userList.get(0).toString();
            model.addAttribute("clientList", userList);
        }
        //model.addAttribute("debugString", debugString);
        return "/users/user";
    }

    @GetMapping("/new")
    public String newClientForm(Card card) {
        card.setUser(new User());
        card.getUser().setDateReg(LocalDateTime.now());
        card.setActivationDate(LocalDateTime.now());
        return "/users/add_user";
    }

    @PostMapping("/save")
    public String saveClient(@Valid Card card, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "/users/add_user";
        }
        userService.save(card.getUser(), card);
        model.addAttribute("clientList", userService.clientList());
        return "redirect:/users/";
    }

    @GetMapping("/edit-user")
    public String editClientForm(@RequestParam Integer id, Model model) {
        //ModelAndView mav = new ModelAndView("edit_client");
        //ClientWithCard clientWithCard = new ClientWithCard(clientService.getUser(id), clientService.getCardByClientId(id));
        //mav.addObject("client", client);
        Card card = this.userService.getCardByClientId(id);
        model.addAttribute("user", card);
        return "/clients/edit_user";
    }

    @GetMapping("/delete")
    public String deleteClient(@RequestParam Integer id) {
        int result = userService.deleteClient(id);
        if (result != 0) {
            //return "redirect:/penalties?id="+id;
            return "redirect:/users/";
        } else {
            return "redirect:/users/";
        }
    }

}


