package ru.ancndz.libraryBase.configs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.ancndz.libraryBase.configs.services.JobService;
import ru.ancndz.libraryBase.configs.services.LibraryService;
import ru.ancndz.libraryBase.configs.services.StaffService;
import ru.ancndz.libraryBase.configs.services.UserService;
import ru.ancndz.libraryBase.content.entity.User;
import ru.ancndz.libraryBase.content.entity.UserExtras;
import ru.ancndz.libraryBase.content.jobs.Job;
import ru.ancndz.libraryBase.content.libraryEnvironment.Library;
import ru.ancndz.libraryBase.content.libraryEnvironment.Staff;

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

    @Autowired
    public RegistrationController(StaffService staffService, UserService userService, LibraryService libraryService, JobService jobService) {
        this.staffService = staffService;
        this.userService = userService;
        this.libraryService = libraryService;
        this.jobService = jobService;
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
        //model.addAttribute("userForm", new User());
        user.setUserExtras(new UserExtras());
        user.getUserExtras().setDateReg(LocalDateTime.now());
        model.addAttribute("user", user);
        return "users/add_user";
    }

    @PostMapping("/save")
    public String addUser(@Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "/users/add_user";
        }
        if (!user.passwordsCheck()){
            model.addAttribute("errorText", "Пароли не совпадают");
            return "/users/add_user";
        }
        if (!userService.save(user)){
            model.addAttribute("errorText", "Пользователь с таким email уже существует");
            return "/users/add_user";
        }
        return "login";
    }

    @PostMapping("/staff/save")
    public String addStaff(@Valid Staff staff, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "/staff/add_staff";
        }
        if (!staff.passwordsCheck()){
            model.addAttribute("errorText", "Пароли не совпадают");
            return "/staff/add_staff";
        }
        staff.setJob(this.jobService.get(staff.getJob_id()));
        staff.setLibrary(this.libraryService.get(staff.getLibrary_id()));
        if (!staffService.save(staff)){
            model.addAttribute("errorText", "Пользователь с таким email уже существует");
            return "/staff/add_staff";
        }
        return "login";
    }
}
