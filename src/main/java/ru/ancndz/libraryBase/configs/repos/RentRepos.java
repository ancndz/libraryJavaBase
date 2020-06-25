package ru.ancndz.libraryBase.configs.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ancndz.libraryBase.content.operations.Rent;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RentRepos extends JpaRepository<Rent, Integer> {
    List<Rent> findAllByLibraryUser_Id(int id);

    List<Rent> findAllByStaff_Id(int id);

    List<Rent> findAllByFactEndDateIsNullAndEndDateBefore(LocalDateTime endDate);

    boolean existsByIdAndFactEndDateIsNotNull(int id);

    Rent getFirstByLibraryUser_Id(int id);

    List<Rent> findAllByBook_AuthorAndBook_NameAndFactEndDateIsNull(String author, String name);

    List<Rent> findAllByBook_Library_IdAndFactEndDateIsNull(int id);
}
