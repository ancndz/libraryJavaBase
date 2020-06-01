package ru.ancndz.libraryBase.configs.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ancndz.libraryBase.content.entity.User;
import ru.ancndz.libraryBase.content.operations.Rent;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RentRepos extends JpaRepository<Rent, Integer> {
    List<Rent> findAllByUser_Id(int id);

    List<Rent> findAllByStaff_Id(int id);

    List<Rent> findAllByFactEndDateIsNullAndEndDateBefore(LocalDateTime endDate);

    boolean existsByIdAndFactEndDateIsNotNull(int id);

    Rent getFirstByUser_Id(int id);
}
