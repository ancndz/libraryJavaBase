package ru.ancndz.libraryBase.configs.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ancndz.libraryBase.configs.repos.RoleRepository;
import ru.ancndz.libraryBase.content.jobs.Role;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> findAll() {
        ifEmpty();
        return this.roleRepository.findAll();
    }

    public Set<Role> findUserRole() {
        ifEmpty();
        return Collections.singleton(this.roleRepository.getOne(1));
    }

    private void ifEmpty() {
        if (this.roleRepository.findAll().isEmpty()) {
            Role roleAdmin = new Role();
            roleAdmin.setId(0);
            roleAdmin.setName("ROLE_ADMIN");
            this.roleRepository.save(roleAdmin);

            Role role = new Role();
            role.setId(1);
            role.setName("ROLE_USER");
            this.roleRepository.save(role);
        }
    }
}
