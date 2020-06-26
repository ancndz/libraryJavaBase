package ru.ancndz.libraryBase.configs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ancndz.libraryBase.configs.services.JobService;
import ru.ancndz.libraryBase.configs.services.LibraryService;
import ru.ancndz.libraryBase.configs.services.RentService;
import ru.ancndz.libraryBase.configs.services.StaffService;
import ru.ancndz.libraryBase.content.entity.LibraryUser;
import ru.ancndz.libraryBase.content.entity.Staff;
import ru.ancndz.libraryBase.content.operations.Rent;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/staff")
public class StaffController {

    private final StaffService staffService;
    private final LibraryService libraryService;
    private final JobService jobService;
    private final RentService rentService;

    @Autowired
    public StaffController(StaffService staffService, LibraryService libraryService, JobService jobService, RentService rentService) {
        this.staffService = staffService;
        this.libraryService = libraryService;
        this.jobService = jobService;
        this.rentService = rentService;
    }

    @GetMapping("")
    public String home(Model model){
        List<Staff> staffList = this.staffService.staffList();
        if (!staffList.isEmpty()) {
            model.addAttribute("staffList", staffList);
        }
        return "staff/staff";
    }

    @GetMapping("/new")
    public String getNewStaffForm(Staff staff, Model model) {
        /*List<Library> libraryList = this.libraryService.libraryList();
        List<Job> jobList = this.jobService.jobList();
        model.addAttribute("libraries", libraryList);
        model.addAttribute("jobs", jobList);
        return "/staff/add_staff";*/
        return "redirect:/registration/staff";
    }

    @GetMapping("/works")
    public String getWorks(@RequestParam(value = "id") int id, @RequestParam(value = "time") String duration, Model model) {
        ChronoUnit chronoUnit;
        switch (duration) {
            case "week": chronoUnit = ChronoUnit.WEEKS;
                break;
            case "month": chronoUnit = ChronoUnit.MONTHS;
                break;
            case "year": chronoUnit = ChronoUnit.YEARS;
                break;
            default: chronoUnit = ChronoUnit.MINUTES;
                break;
        }
        //List<Staff> allStaff = this.staffService.staffList();
        Staff staff = this.staffService.get(id);
        List<Rent> allRents;
        //Map<Staff, Integer> allStaffWithWorks = new HashMap<>();
        Set<LibraryUser> staffLibraryUsers = new HashSet<>();
        //for (Staff staff: allStaff) {
            int countRents = 0;
            allRents = this.rentService.getAllByStaffId(staff.getId());
            if (allRents != null && !allRents.isEmpty()) {
                for (Rent rent: allRents) {
                    if (rent.getStartDate().plus(1, chronoUnit).isAfter(LocalDateTime.now())) {
                        countRents += 1;
                        staffLibraryUsers.add(rent.getLibraryUser());
                    }
                }
            }
            //allStaffWithWorks.put(staff, countRents);
        //}
        model.addAttribute("staff", staff);
        model.addAttribute("works", countRents);
        model.addAttribute("staffUsers", staffLibraryUsers);
        return "staff/works";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam int id) {
        this.staffService.delete(id);
        return "redirect:/staff/";
    }

    @GetMapping("edit")
    public String edit(@RequestParam int id, Model model) {
        model.addAttribute("staff", this.staffService.get(id));
        model.addAttribute("libs", this.libraryService.libraryList());
        model.addAttribute("jobs", this.jobService.jobList());
        return "staff/edit_staff";
    }
}
