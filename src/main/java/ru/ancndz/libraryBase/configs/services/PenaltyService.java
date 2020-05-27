package ru.ancndz.libraryBase.configs.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ancndz.libraryBase.configs.repos.PenaltyRepository;
import ru.ancndz.libraryBase.configs.repos.RentRepos;
import ru.ancndz.libraryBase.content.operations.Penalty;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PenaltyService {
    private final PenaltyRepository penaltyRepository;
    private final RentRepos rentRepos;

    @Autowired
    public PenaltyService(PenaltyRepository penaltyRepository, RentRepos rentRepos) {
        this.penaltyRepository = penaltyRepository;
        this.rentRepos = rentRepos;
    }

    public List<Penalty> penaltyList() {
        return (List<Penalty>) this.penaltyRepository.findAll();
    }

    public Penalty get(int id) {
        return this.penaltyRepository.getOne(id);
    }

    public List<Penalty> unpaidList() {
        return this.penaltyRepository.findAllByPayDateIsNull();
    }

    public void payPenalty(int id, int amount) {
        Penalty penalty = this.penaltyRepository.getOne(id);
        penalty.setCompleteAmount(penalty.getCompleteAmount() + amount);
        if (penalty.getAmount() <= penalty.getCompleteAmount()) {
            if (this.rentRepos.existsByIdAndFactEndDateIsNotNull(penalty.getRent().getId())) {
                penalty.setPayDate(LocalDateTime.now());
            }
        }
        this.penaltyRepository.save(penalty);
    }

    public void save(Penalty penalty) {
        this.penaltyRepository.save(penalty);
    }

    public List<Penalty> getAllByUserId(int id) {
        return this.penaltyRepository.findAllByUser_IdAndPayDateIsNull(id);
    }
}
