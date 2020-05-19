package ru.ancndz.libraryBase.configs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ancndz.libraryBase.configs.services.ClientService;
import ru.ancndz.libraryBase.content.entity.Client;
import ru.ancndz.libraryBase.content.entity.nonbusiness.User;
import ru.ancndz.libraryBase.content.libraryEnvironment.Card;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/users")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("")
    public String usersHome(Model model) {
        List<Client> clientList = clientService.clientList();
        //String debugString = "";
        if (!clientList.isEmpty()) {
            //debugString = clientList.get(0).toString();
            model.addAttribute("clientList", clientList);
        }
        //model.addAttribute("debugString", debugString);
        return "clients";
    }

    @GetMapping("/new")
    public String newClientForm(Client client, Card card, User user) {
        client.setDateReg(LocalDateTime.now());
        card.setActivationDate(LocalDateTime.now());
        return "new_client";
    }

    @PostMapping("/save")
    public String saveClient(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "new_client";
        }
        clientService.save(user.getClient(), user.getCard());
        model.addAttribute("clientList", clientService.clientList());
        return "redirect:/users";
    }

    @GetMapping("/edit-user")
    public String editClientForm(@RequestParam Integer id, Model model) {
        //ModelAndView mav = new ModelAndView("edit_client");
        User user = new User(clientService.getClient(id), clientService.getCardByClientId(id));
        //mav.addObject("client", client);
        model.addAttribute("user", user);
        return "edit_client";
    }

    @GetMapping("/delete")
    public String deleteClient(@RequestParam Integer id) {
        int result = clientService.deleteClient(id);
        if (result != 0) {
            return "redirect:/penalties?id="+id;
        } else {
            return "redirect:/users";
        }
    }

}


