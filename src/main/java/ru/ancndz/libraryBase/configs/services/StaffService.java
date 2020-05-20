package ru.ancndz.libraryBase.configs.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ancndz.libraryBase.configs.repos.StaffRepository;
import ru.ancndz.libraryBase.content.libraryEnvironment.Staff;

import java.util.List;

@Service
public class StaffService {
    private final StaffRepository staffRepository;

    @Autowired
    public StaffService(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    public void save(Staff staff) {
        this.staffRepository.save(staff);
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
