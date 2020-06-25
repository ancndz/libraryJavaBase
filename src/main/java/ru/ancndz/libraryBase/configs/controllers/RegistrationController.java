package ru.ancndz.libraryBase.configs.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.ancndz.libraryBase.configs.services.*;
import ru.ancndz.libraryBase.content.entity.User;
import ru.ancndz.libraryBase.content.entity.UserExtras;
import ru.ancndz.libraryBase.content.jobs.Job;
import ru.ancndz.libraryBase.content.libraryEnvironment.Library;
import ru.ancndz.libraryBase.content.libraryEnvironment.Staff;

import javax.jws.WebParam;
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
        if (userDetails instanceof User) {
            User user = (User) userDetails;
            if (bCryptPasswordEncoder.matches((CharSequence) userService.get(user.getId()), user.getPassword())) {
                user.setPassword(bCryptPasswordEncoder.encode(user.getPasswordConfirm()));
                user.setPasswordConfirm(bCryptPasswordEncoder.encode(user.getPasswordConfirm()));
                this.userService.save(user);
            } else {
                model.addAttribute("error", "Wrong password!");
                return "users/change_password";
            }
        } else if (userDetails instanceof Staff) {
            Staff staff = (Staff) userDetails;
            if (bCryptPasswordEncoder.matches((CharSequence) staffService.get(staff.getId()), staff.getPassword())) {
                staff.setPassword(bCryptPasswordEncoder.encode(staff.getPasswordConfirm()));
                staff.setPasswordConfirm(bCryptPasswordEncoder.encode(staff.getPasswordConfirm()));
                this.staffService.save(staff);
            } else {
                model.addAttribute("error", "Wrong password!");
                return "users/change_password";
            }
        }
        return "mainPage";
    }

    @GetMapping("/staff")
    public String registrationStaff(Staff staff, Model model) {
        List<Library> libraryList = this.libraryService.libraryList();
        if (libraryList.isEmpty()) {
            Library lib = new Library("none", "none");
            this.libraryService.save(lib);
            staff.setLibrary(lib);
        }
        List<Job> jobList = this.jobService.jobList();
        if (jobList.isEmpty()) {
            Job job = new Job("none", 0);
            this.jobService.save(job);
            staff.setJob(job);
        }
        model.addAttribute("libraries", libraryList);
        model.addAttribute("jobs", jobList);
        model.addAttribute("staff", staff);
        return "staff/add_staff";
    }

    @GetMapping("")
    public String registrationUser(User user, Model model) {
        this.staffService.checkEmpty();
        user.setUserExtras(new UserExtras());
        user.getUserExtras().setDateReg(LocalDateTime.now());
        model.addAttribute("user", user);
        return "users/add_user";
    }

    @PostMapping("/save")
    public String addUser(@Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "users/add_user";
        }
        if (!user.passwordsCheck()){
            model.addAttribute("errorText", "Пароли не совпадают");
            return "users/add_user";
        }
        if (!userService.save(user)){
            model.addAttribute("errorText", "Пользователь с таким email уже существует");
            return "users/add_user";
        }
        return "login";
    }

    @PostMapping("/staff/save")
    public String addStaff(@Valid Staff staff, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "staff/add_staff";
        }
        if (!staff.passwordsCheck()){
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
