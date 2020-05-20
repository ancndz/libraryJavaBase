package ru.ancndz.libraryBase.configs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
        List<Job> jobList = this.jobService.jobList();
        if (!jobList.isEmpty()) {
            model.addAttribute("jobs", jobList);
        }
        return "jobs";
    }

    @GetMapping("/new")
    public String newJobForm(Job job) {
        return "add_job";
    }

    @PostMapping("/save")
    public String save(@Valid Job job, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add_job";
        }
        jobService.save(job);
        return "redirect:/jobs";
    }
}
