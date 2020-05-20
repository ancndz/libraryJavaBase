package ru.ancndz.libraryBase.content.libraryEnvironment;

import ru.ancndz.libraryBase.content.entity.User;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    @OneToOne(fetch = FetchType.LAZY, targetEntity = User.class, cascade = CascadeType.DETACH)
    @JoinColumn(name = "client_id", unique = true, nullable = false)
    private User user;

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
     * @param user клиент (владелец карты)
     */
    public Card(int id, String code, User user) {
        this.id = id;
        this.activationDate = LocalDateTime.now();
        this.code = code;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return getUser().equals(card.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser(), "_libraryCard");
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", activationDate=" + activationDate +
                ", code='" + code + '\'' +
                ", user=" + user +
                '}';
    }
}
