package ru.ancndz.libraryBase.configs.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ancndz.libraryBase.configs.repos.PenaltyRepository;
import ru.ancndz.libraryBase.configs.repos.RentRepos;
import ru.ancndz.libraryBase.content.operations.Rent;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RentService {
    private final RentRepos rentRepos;
    private final PenaltyRepository penaltyRepository;

    @Autowired
    public RentService(RentRepos rentRepos, PenaltyRepository penaltyRepository) {
        this.rentRepos = rentRepos;
        this.penaltyRepository = penaltyRepository;
    }

    public boolean save(Rent rent) {
        if (!this.penaltyRepository.findAllByUser_IdAndPayDateIsNotNull(rent.getUser().getId()).isEmpty()) {
            return false;
        } else {
            this.rentRepos.save(rent);
            return true;
        }
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

    public List<Rent> getAllByUserId(int id) {
        return this.rentRepos.findAllByUser_Id(id);
    }

    public void close(int id) {
        this.rentRepos.getOne(id).setFactEndDate(LocalDateTime.now());
    }
}
