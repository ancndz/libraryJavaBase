package ru.ancndz.libraryBase.configs.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ancndz.libraryBase.configs.repos.JobRepository;
import ru.ancndz.libraryBase.content.jobs.Job;

import java.util.List;

@Service
public class JobService {
    private final JobRepository jobRepository;

    @Autowired
    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public void save(Job job) {
        this.jobRepository.save(job);
    }

    public List<Job> jobList() {
        checkEmpty();
        return this.jobRepository.findAll();
    }

    public List<Job> jobListBusiness() {
        checkEmpty();
        return this.jobRepository.findAllByIdIsNot(1);
    }

    private void checkEmpty() {
        if (this.jobRepository.findAll().isEmpty()) {
            Job job = new Job();
            job.setId(1);
            job.setName("none");
            job.setPay(0);
            this.jobRepository.save(job);
        }
    }

    public Job get(int id) {
        checkEmpty();
        return this.jobRepository.getOne(id);
    }

    public void delete(int id) {
        this.jobRepository.deleteById(id);
    }
}
