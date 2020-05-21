package ru.ancndz.libraryBase.configs.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ancndz.libraryBase.configs.repos.ReservRepository;
import ru.ancndz.libraryBase.content.operations.Reservations;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservService {
    private final ReservRepository reservRepository;

    @Autowired
    public ReservService(ReservRepository reservRepository) {
        this.reservRepository = reservRepository;
    }

    public void save(Reservations reservations) {
        this.reservRepository.save(reservations);
    }

    /*public void autodelete() {
        List<Reservations> reservations = this.reservRepository.findAllByEndDateIsBefore(LocalDateTime.now());
        for(Reservations each: reservations) {
            delete(each);
        }
    }

    public List<Reservations> getByBook(int id) {
        return this.reservRepository.findAllByBook_idAndEndDateIsAfter(LocalDateTime.now());
    }
*/
    public void delete(Reservations reservations) {
        this.reservRepository.delete(reservations);
    }

    public void deleteById(int id) {
        this.reservRepository.deleteById(id);
    }
}
