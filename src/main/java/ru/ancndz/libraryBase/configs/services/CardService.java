package ru.ancndz.libraryBase.configs.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ancndz.libraryBase.configs.repos.CardRepository;
import ru.ancndz.libraryBase.content.libraryEnvironment.Card;

import java.util.List;

@Service
public class CardService {
    private final CardRepository cardRepository;

    @Autowired
    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public void save(Card card) {
        cardRepository.save(card);
    }
    public List<Card> cardList() {
        return (List<Card>) cardRepository.findAll();
    }
    public Card get(Integer id) {
        return cardRepository.findById(id).get();
    }
    public void delete(Integer id) {
        cardRepository.deleteById(id);
    }
}
