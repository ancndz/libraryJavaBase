package ru.ancndz.libraryBase.configs.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ancndz.libraryBase.configs.repos.CardRepository;
import ru.ancndz.libraryBase.content.entity.User;
import ru.ancndz.libraryBase.configs.repos.UserRepository;
import ru.ancndz.libraryBase.content.libraryEnvironment.Card;

import java.util.List;

@Service
public class UserService {
    private final UserRepository repository;
    private final CardRepository cardRepository;

    @Autowired
    public UserService(UserRepository repository, CardRepository cardRepository) {
        this.repository = repository;
        this.cardRepository = cardRepository;
    }

    public void save(User user, Card card) {
        if (this.repository.findUserByEmail(user.getEmail()) == null ||
                this.repository.findUserByEmail(user.getEmail()).getId() == user.getId()) {
            repository.save(user);
            cardRepository.save(card);
        }
    }

    public List<User> clientList() {
        return (List<User>) repository.findAll();
    }

    public List<Card> cardList() {
        return (List<Card>) cardRepository.findAll();
    }

    public User getClient(Integer id) {
        return repository.findById(id).get();
    }

    public Card getCard(Integer id) {
        return cardRepository.findById(id).get();
    }

    public Card getCardByClientId(int id) {
        return cardRepository.getCardByClient_Id(id);
    }

    public void deleteCard(Integer id) {
        cardRepository.deleteById(id);
    }

    public int deleteClient(Integer id) {
        return repository.unregisterClient(id);
    }

}
