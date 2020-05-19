package ru.ancndz.libraryBase.content.entity.nonbusiness;

import ru.ancndz.libraryBase.content.entity.Client;
import ru.ancndz.libraryBase.content.libraryEnvironment.Card;

public class User {
    
    private Client client;
    private Card card;

    public User() {
    }
    
    public User(Client client, Card card) {
        this.client = client;
        this.card = card;
        this.card.setClient(client);
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        if (this.card != null) {
            this.card.setClient(client);
        }
        this.client = client;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
        if (this.client != null) {
            this.card.setClient(this.client);
        }
    }
}
