package ru.ancndz.libraryBase.configs.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ancndz.libraryBase.configs.repos.RentRepos;
import ru.ancndz.libraryBase.content.operations.Rent;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RentService {
    private final RentRepos rentRepos;

    @Autowired
    public RentService(RentRepos rentRepos) {
        this.rentRepos = rentRepos;
    }

    public void save(Rent rent) {
        this.rentRepos.save(rent);
    }

    public List<Rent> rentList() {
        return (List<Rent>) this.rentRepos.findAll();
    }

    public void delete(int id) {
        this.rentRepos.deleteById(id);
    }

    public Rent get(int id) {
        return this.rentRepos.getOne(id);
    }
}
