package ru.ancndz.libraryBase.content.libraryEnvironment;

import org.springframework.data.annotation.Transient;
import ru.ancndz.libraryBase.content.entity.Client;
import ru.ancndz.libraryBase.content.operations.Penalty;
import ru.ancndz.libraryBase.content.operations.Rent;

import javax.annotation.Generated;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Класс читательской карты клиента, привязана к клиенту и все аренды и штрафы назначаются на нее
 */
@Entity
@Table(name = "card")
public class Card {
    /**
     * айди карточки клиента
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    /**
     * дата активации
     */
    @Column(name = "activation_date")
    private LocalDateTime activationDate;
    /**
     * секретное слово
     */
    @Column(name = "code")
    private String code;
    /**
     * клиент, владелец карты
     */
    @OneToOne(fetch = FetchType.LAZY, targetEntity = Client.class, cascade = CascadeType.DETACH)
    @JoinColumn(name = "client_id", unique = true, nullable = false)
    private Client client;

    /**
     * non-arg constructor
     */
    public Card(){
        this.activationDate = LocalDateTime.now();
    }

    /**
     * конструктор читательской карты клиента
     * @param id айди карты
     * //@param activationDate дата активации
     * @param code секретное слово
     * @param client клиент (владелец карты)
     */
    public Card(int id, String code, Client client) {
        this.id = id;
        this.activationDate = LocalDateTime.now();
        this.code = code;
        this.client = client;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(LocalDateTime activationDate) {
        this.activationDate = activationDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return getClient().equals(card.getClient());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClient(), "_libraryCard");
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", activationDate=" + activationDate +
                ", code='" + code + '\'' +
                ", client=" + client +
                '}';
    }
}
