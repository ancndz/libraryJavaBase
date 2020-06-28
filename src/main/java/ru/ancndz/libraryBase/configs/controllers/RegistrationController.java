package ru.ancndz.libraryBase.configs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.ancndz.libraryBase.configs.services.*;
import ru.ancndz.libraryBase.content.entity.LibraryUser;
import ru.ancndz.libraryBase.content.entity.Staff;
import ru.ancndz.libraryBase.content.entity.UserExtras;
import ru.ancndz.libraryBase.content.jobs.Job;
import ru.ancndz.libraryBase.content.libraryEnvironment.Library;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private final StaffService staffService;
    private final UserService userService;
    private final LibraryService libraryService;
    private final JobService jobService;
    private final LoginService loginService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public RegistrationController(StaffService staffService, UserService userService, LibraryService libraryService, JobService jobService, LoginService loginService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.staffService = staffService;
        this.userService = userService;
        this.libraryService = libraryService;
        this.jobService = jobService;
        this.loginService = loginService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping("/change_password")
    public String changePass(Authentication authentication, Model model) {
        model.addAttribute(loginService.loadByAuth(authentication));
        return "users/change_password";
    }

    @PostMapping("/change_password")
    public String savePass(@Valid UserDetails userDetails, BindingResult bindingResult, Model model) {
        if (userDetails instanceof LibraryUser) {
            LibraryUser libraryUser = (LibraryUser) userDetails;
            if (bCryptPasswordEncoder.matches((CharSequence) userService.get(libraryUser.getId()), libraryUser.getPassword())) {
                libraryUser.setPassword(bCryptPasswordEncoder.encode(libraryUser.getPasswordConfirm()));
                libraryUser.setPasswordConfirm(bCryptPasswordEncoder.encode(libraryUser.getPasswordConfirm()));
                this.userService.save(libraryUser);
            } else {
                model.addAttribute("error", "Wrong password!");
                return "users/change_password";
            }
        }
        return "mainPage";
    }

    @GetMapping("/staff")
    public String registrationStaff(Staff staff, Model model) {
        staff.setUserExtras(new UserExtras());
        staff.getUserExtras().setDateReg(LocalDateTime.now());
        List<Library> libraryList = this.libraryService.libraryList();
        List<Job> jobList = this.jobService.jobList();
        model.addAttribute("libraries", libraryList);
        model.addAttribute("jobs", jobList);
        model.addAttribute("staff", staff);
        return "staff/add_staff";
    }

    @GetMapping("")
    public String registrationUser(LibraryUser libraryUser, Model model) {
        this.staffService.checkEmpty();
        libraryUser.setUserExtras(new UserExtras());
        libraryUser.getUserExtras().setDateReg(LocalDateTime.now());
        model.addAttribute("user", libraryUser);
        return "users/add_user";
    }

    @PostMapping("/save")
    public String addUser(@Valid LibraryUser libraryUser, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "users/add_user";
        }
        if (!libraryUser.passwordsCheck()) {
            model.addAttribute("errorText", "Пароли не совпадают");
            return "users/add_user";
        }
        if (!userService.save(libraryUser)) {
            model.addAttribute("errorText", "Пользователь с таким email уже существует");
            return "users/add_user";
        }
        return "login";
    }

    @PostMapping("/staff/save")
    public String addStaff(@Valid Staff staff, BindingResult bindingResult, Model model) {
        staff.getUserExtras().setStatus("Сотрудник библиотеки");
        if (bindingResult.hasErrors()) {
            return "staff/add_staff";
        }
        if (!staff.passwordsCheck()) {
            model.addAttribute("errorText", "Пароли не совпадают");
            return "staff/add_staff";
        }
        staff.setJob(this.jobService.get(staff.getJob_id()));
        staff.setLibrary(this.libraryService.get(staff.getLibrary_id()));
        if (!staffService.save(staff)){
            model.addAttribute("errorText", "Пользователь с таким email уже существует");
            return "staff/add_staff";
        }
        return "login";
    }
}
