package ru.ancndz.libraryBase.configs.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ancndz.libraryBase.content.jobs.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {
}
