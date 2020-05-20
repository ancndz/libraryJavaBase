package ru.ancndz.libraryBase.configs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.ancndz.libraryBase.configs.services.JobService;
import ru.ancndz.libraryBase.configs.services.LibraryService;
import ru.ancndz.libraryBase.configs.services.StaffService;
import ru.ancndz.libraryBase.content.jobs.Job;
import ru.ancndz.libraryBase.content.libraryEnvironment.Library;
import ru.ancndz.libraryBase.content.libraryEnvironment.Staff;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/staff")
public class StaffController {

    private final StaffService staffService;
    private final LibraryService libraryService;
    private final JobService jobService;

    @Autowired
    public StaffController(StaffService staffService, LibraryService libraryService, JobService jobService) {
        this.staffService = staffService;
        this.libraryService = libraryService;
        this.jobService = jobService;
    }

    @GetMapping("")
    public String home(Model model){
        List<Staff> staffList = this.staffService.staffList();
        if (!staffList.isEmpty()) {
            model.addAttribute("staffList", staffList);
        }
        return "staff";
    }

    @GetMapping("/new")
    public String getNewStaffForm(Staff staff, Model model) {
        List<Library> libraryList = this.libraryService.libraryList();
        List<Job> jobList = this.jobService.jobList();
        model.addAttribute("libraries", libraryList);
        model.addAttribute("jobs", jobList);
        return "add_staff";
    }

    @PostMapping("/save")
    public String saveStaff(@Valid Staff staff, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("error", result.toString());
            return "add_staff";
        }
        staff.setJob(this.jobService.get(staff.getJob_id()));
        staff.setLibrary(this.libraryService.get(staff.getLibrary_id()));
        this.staffService.save(staff);
        return "redirect:/staff";
    }
}
