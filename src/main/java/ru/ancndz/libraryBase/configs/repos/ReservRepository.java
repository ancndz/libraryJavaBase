package ru.ancndz.libraryBase.configs.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ancndz.libraryBase.content.operations.Reservations;

import java.beans.Transient;
import java.time.LocalDateTime;
import java.util.List;

public interface ReservRepository extends JpaRepository<Reservations, Integer> {
    /**
     * get all active users reservs
     * @param id user id
     * @param date now()
     * @return list
     */
    /*List<Reservations> findAllByUser_IdAndEndDateIsAfter(int id, LocalDateTime date);

    List<Reservations> findAllByEndDateIsBefore(LocalDateTime date);

    List<Reservations> findAllByBook_idAndEndDateIsAfter(LocalDateTime date);*/
}
