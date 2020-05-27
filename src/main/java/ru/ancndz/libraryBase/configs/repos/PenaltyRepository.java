package ru.ancndz.libraryBase.configs.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ancndz.libraryBase.content.operations.Penalty;

import java.util.List;

@Repository
public interface PenaltyRepository extends JpaRepository<Penalty, Integer> {
    List<Penalty> findAllByUser_IdAndPayDateIsNull(int id);

    boolean existsAllByUser_IdAndPayDateIsNull(int id);

    Penalty findByRent_IdAndReasonEqualsAndAmount(int id, String reason, int amount);

    List<Penalty> findAllByPayDateIsNull();

    Penalty findByRent_IdAndReasonEqualsAndPayDateIsNull(int id, String reason);
}
