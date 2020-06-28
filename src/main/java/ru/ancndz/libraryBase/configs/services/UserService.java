package ru.ancndz.libraryBase.configs.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ancndz.libraryBase.configs.repos.UserRepository;
import ru.ancndz.libraryBase.content.entity.LibraryUser;

import java.util.List;

@Service
public class UserService {
    private final UserRepository repository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleService roleService;

    @Autowired
    public UserService(UserRepository repository, BCryptPasswordEncoder bCryptPasswordEncoder, RoleService roleService) {
        this.repository = repository;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public boolean save(LibraryUser libraryUser) {
        if (this.repository.findByEmail(libraryUser.getEmail()) != null &&
                this.repository.findByEmail(libraryUser.getEmail()).getId() != libraryUser.getId()) {
            return false;
        }
        libraryUser.setRoles(this.roleService.findUserRole());
        libraryUser.setPassword(bCryptPasswordEncoder.encode(libraryUser.getPassword()));
        libraryUser.setPasswordConfirm(bCryptPasswordEncoder.encode(libraryUser.getPassword()));
        repository.save(libraryUser);
        return true;
    }

    public LibraryUser getByEmail(String email) {
        return this.repository.findByEmail(email);
    }

    public void deleteByExtras(int id) {
        this.repository.deleteByUserExtras_Id(id);
    }

    public void delete(int id) {
        this.repository.deleteById(id);
    }

    public LibraryUser getByExtras(int id) {
        return this.repository.getByUserExtras_Id(id);
    }

    public LibraryUser get(int id) {
        return this.repository.getOne(id);
    }

    public List<LibraryUser> getAll() {
        return this.repository.findAll();
    }

}
