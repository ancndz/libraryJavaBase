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
        return (List<Job>) this.jobRepository.findAll();
    }

    public Job get(int id) {
        return this.jobRepository.getOne(id);
    }

    public void delete(int id) {
        this.jobRepository.deleteById(id);
    }
}
