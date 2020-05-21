package ru.ancndz.libraryBase.configs.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ancndz.libraryBase.content.operations.Rent;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RentRepos extends JpaRepository<Rent, Integer> {
    /**
     * @param id - айди клиента
     * @return лист аренд
     */
    //List<Rent> findAllByCard_Client_Id(int id);

    //List<Rent> findAllByFactEndDateIsNullAndCard_Client_Id(int id);

    //List<Rent> findAllByEndDateIsBeforeAndFactEndDateIsNullAndCard_Client_Id(LocalDateTime now, int id);
}
