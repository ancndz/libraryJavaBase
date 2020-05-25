package ru.ancndz.libraryBase.configs.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ancndz.libraryBase.configs.repos.RoleRepository;
import ru.ancndz.libraryBase.configs.repos.StaffRepository;
import ru.ancndz.libraryBase.content.entity.User;
import ru.ancndz.libraryBase.content.jobs.Role;
import ru.ancndz.libraryBase.content.libraryEnvironment.Staff;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class StaffService {

    private final StaffRepository staffRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleRepository repository;


    @Autowired
    public StaffService(StaffRepository staffRepository, RoleRepository repository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.staffRepository = staffRepository;
        this.repository = repository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public boolean save(Staff staff) {
        if (this.staffRepository.findByEmail(staff.getEmail()) != null &&
                this.staffRepository.findByEmail(staff.getEmail()).getId() != staff.getId()) {
            return false;
        }
        staff.setRoles(new HashSet<>(this.repository.findAll()));
        staff.setPassword(bCryptPasswordEncoder.encode(staff.getPassword()));
        staff.setPasswordConfirm(bCryptPasswordEncoder.encode(staff.getPassword()));
        staffRepository.save(staff);
        return true;
    }

    public Staff get(Integer id) {
        return this.staffRepository.getOne(id);
    }

    public void delete(Integer id) {
        this.staffRepository.deleteById(id);
    }

    public List<Staff> staffList() {
        return (List<Staff>) this.staffRepository.findAll();
    }

    public List<String> staffListByLibrary(int id) {
        return this.staffListByLibrary(id);
    }

}
