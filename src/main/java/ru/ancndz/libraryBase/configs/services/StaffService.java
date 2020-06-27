package ru.ancndz.libraryBase.configs.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ancndz.libraryBase.configs.repos.StaffRepository;
import ru.ancndz.libraryBase.configs.repos.UserRepository;
import ru.ancndz.libraryBase.content.entity.Staff;
import ru.ancndz.libraryBase.content.entity.UserExtras;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@Service
public class StaffService {

    private final StaffRepository staffRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleService roleService;
    private final UserRepository userRepository;
    private final JobService jobService;
    private final LibraryService libraryService;

    @Autowired
    public StaffService(StaffRepository staffRepository, BCryptPasswordEncoder bCryptPasswordEncoder, RoleService roleService, UserRepository userRepository, JobService jobService, LibraryService libraryService) {
        this.staffRepository = staffRepository;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.jobService = jobService;
        this.libraryService = libraryService;

    }

    public void checkEmpty() {
        if (staffRepository.findAll().isEmpty()) {
            Staff staff = new Staff();
            UserExtras userExtras = new UserExtras();
            userExtras.setDateReg(LocalDateTime.now());
            userExtras.setFirstName("Admin");
            userExtras.setLastName("Local");
            userExtras.setId(1);
            userExtras.setStatus("Main Default Admin");
            staff.setUserExtras(userExtras);
            staff.setEmail("admin@admin.com");
            staff.setAddress("localhost");
            staff.setNumber(19216810010L);
            staff.setPassword("12345");
            staff.setPasswordConfirm("12345");
            staff.setId(1);
            staff.setJob(this.jobService.get(1));
            staff.setJob_id(1);
            staff.setLibrary(this.libraryService.get(1));
            staff.setLibrary_id(1);
            save(staff);
        }
    }

    public boolean save(Staff staff) {
        if (this.userRepository.findByEmail(staff.getEmail()) != null) {
            return false;
        }
        staff.setRoles(new HashSet<>(this.roleService.findAll()));
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
        checkEmpty();
        return this.staffRepository.findAll();
    }

}
