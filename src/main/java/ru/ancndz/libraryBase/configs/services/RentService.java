package ru.ancndz.libraryBase.configs.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ancndz.libraryBase.configs.repos.PenaltyRepository;
import ru.ancndz.libraryBase.configs.repos.RentRepos;
import ru.ancndz.libraryBase.content.operations.Penalty;
import ru.ancndz.libraryBase.content.operations.Rent;

import java.time.Duration;
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
        //if (!this.penaltyRepository.findAllByUser_IdAndPayDateIsNull(rent.getUser().getId()).isEmpty()) {
        if (!this.penaltyRepository.existsAllByUser_IdAndPayDateIsNull(rent.getUser().getId())) {
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
        Penalty penalty = this.penaltyRepository.findByRent_IdAndReasonEqualsAndAmount(id, "_rent_expired", 0);
        if (penalty != null) {
            penalty.setPayDate(LocalDateTime.now());
            this.penaltyRepository.save(penalty);
        }
    }

    public void findOutdatedRents() {
        List<Rent> outDatedRents = this.rentRepos.findAllByFactEndDateIsNullAndEndDateBefore(LocalDateTime.now());
        for (Rent each: outDatedRents) {
            Duration duration = Duration.between(LocalDateTime.now(), each.getEndDate());
            //int amount = (int) duration.toDays() * 30;
            //todo change back to days
            int amount = (int) duration.toMinutes() * 6;
            amount = Math.abs(amount);
            Penalty penalty = this.penaltyRepository.
                    findByRent_IdAndReasonEqualsAndPayDateIsNull(each.getId(), "_rent_expired");
            if (penalty == null) {
                penalty = new Penalty();
                penalty.setReason("_rent_expired");
                penalty.setRent(each);
                penalty.setUser(each.getUser());
                penalty.setAmount(amount);
                penalty.setDate(LocalDateTime.now());
            } else {
                penalty.setAmount(amount);
            }
            this.penaltyRepository.save(penalty);
        }
    }
}
