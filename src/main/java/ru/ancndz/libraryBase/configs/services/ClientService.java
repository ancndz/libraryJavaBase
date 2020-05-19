package ru.ancndz.libraryBase.configs.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ancndz.libraryBase.configs.repos.CardRepository;
import ru.ancndz.libraryBase.content.entity.Client;
import ru.ancndz.libraryBase.configs.repos.ClientRepository;
import ru.ancndz.libraryBase.content.libraryEnvironment.Card;

import java.util.List;

@Service
public class ClientService {
    private final ClientRepository repository;
    private final CardRepository cardRepository;

    @Autowired
    public ClientService(ClientRepository repository, CardRepository cardRepository) {
        this.repository = repository;
        this.cardRepository = cardRepository;
    }

    public void save(Client client, Card card) {
        repository.save(client);
        cardRepository.save(card);
    }

    public List<Client> clientList() {
        return (List<Client>) repository.findAll();
    }

    public List<Card> cardList() {
        return (List<Card>) cardRepository.findAll();
    }

    public Client getClient(Integer id) {
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
