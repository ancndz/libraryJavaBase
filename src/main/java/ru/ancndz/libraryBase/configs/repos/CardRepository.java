package ru.ancndz.libraryBase.configs.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ancndz.libraryBase.content.libraryEnvironment.Card;

@Repository
public interface CardRepository extends JpaRepository<Card, Integer> {
    Card getCardByClient_Id(int id);
}
