package ru.ancndz.libraryBase.configs.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ancndz.libraryBase.configs.repos.RoleRepository;
import ru.ancndz.libraryBase.configs.repos.StaffRepository;
import ru.ancndz.libraryBase.configs.repos.UserRepository;
import ru.ancndz.libraryBase.content.entity.User;
import ru.ancndz.libraryBase.content.jobs.Role;

import java.util.Collections;
import java.util.List;

@Service
public class UserService {
    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final StaffRepository staffRepository;

    @Autowired
    public UserService(UserRepository repository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder, StaffRepository staffRepository) {
        this.repository = repository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.staffRepository = staffRepository;
    }

    public boolean save(User user) {
        if (this.repository.findByEmail(user.getEmail()) != null &&
            this.repository.findByEmail(user.getEmail()).getId() != user.getId()) {
            return false;
        } else if (this.staffRepository.findByEmail(user.getEmail()) != null) {
            return false;
        }
        user.setRoles(Collections.singleton(new Role(2, "ROLE_USER")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setPasswordConfirm(bCryptPasswordEncoder.encode(user.getPassword()));
        repository.save(user);
        return true;
    }

    public User getByEmail(String email) {
        return this.repository.findByEmail(email);
    }

    public void deleteByExtras(int id) {
        this.repository.deleteByUserExtras_Id(id);
    }

    public void delete(int id) {
        this.repository.deleteById(id);
    }

    public User getByExtras(int id) {
        return this.repository.getByUserExtras_Id(id);
    }

    public User get(int id) {
        return this.repository.getOne(id);
    }

    public List<User> getAll() {
        return (List<User>) this.repository.findAll();
    }

}
