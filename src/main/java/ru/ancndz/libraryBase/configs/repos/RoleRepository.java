package ru.ancndz.libraryBase.configs.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ancndz.libraryBase.content.jobs.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
