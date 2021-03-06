package ru.ancndz.libraryBase.configs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ancndz.libraryBase.configs.services.JobService;
import ru.ancndz.libraryBase.content.jobs.Job;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/jobs")
public class JobController {
    private final JobService jobService;

    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping("")
    public String home(Model model) {
        List<Job> jobList = this.jobService.jobListBusiness();
        if (!jobList.isEmpty()) {
            model.addAttribute("jobs", jobList);
        }
        return "jobs/jobs";
    }

    @GetMapping("/new")
    public String newJobForm(Job job) {
        return "jobs/add_job";
    }

    @PostMapping("/save")
    public String save(@Valid Job job, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "jobs/add_job";
        }
        jobService.save(job);
        return "redirect:/jobs/";
    }

    @GetMapping("/edit")
    public String editBookForm(@RequestParam Integer id, Model model) {
        //ModelAndView mav = new ModelAndView("edit_client");
        Job job = jobService.get(id);
        //mav.addObject("client", client);
        model.addAttribute("job", job);
        return "jobs/edit_job";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam int id) {
        this.jobService.delete(id);
        return "redirect:/jobs/";
    }
}
