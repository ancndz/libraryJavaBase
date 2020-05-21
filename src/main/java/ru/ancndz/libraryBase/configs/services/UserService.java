package ru.ancndz.libraryBase.configs.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ancndz.libraryBase.configs.repos.RoleRepository;
import ru.ancndz.libraryBase.configs.repos.UserRepository;
import ru.ancndz.libraryBase.content.entity.User;
import ru.ancndz.libraryBase.content.jobs.Role;

import java.util.Collections;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository repository;
    private final RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository repository, RoleRepository roleRepository) {
        this.repository = repository;
        this.roleRepository = roleRepository;
    }

    public boolean save(User user) {

        if (this.repository.findByEmail(user.getEmail()) != null &&
            this.repository.findByEmail(user.getEmail()).getId() != user.getId()) {
            return false;
        }

        user.setRoles(Collections.singleton(new Role(2, "ROLE_USER")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setPasswordConfirm(bCryptPasswordEncoder.encode(user.getPassword()));
        repository.save(user);
        return true;
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

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = this.repository.findByEmail(s);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;

    }
}
