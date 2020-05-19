package ru.ancndz.libraryBase.content.operations;

import ru.ancndz.libraryBase.content.libraryEnvironment.Card;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Класс штрафа, имеет айди аренды, к которой привязан и айди карты
 */
@Entity
@Table(name = "penalty")
public class Penalty {
    /**
     * айди штрафа
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    /**
     * причина штрафа
     */
    @Column(name = "reason")
    private String reason;
    /**
     * дата штрафа
     */
    @Column(name = "date")
    private LocalDateTime date;
    /**
     * дата вылаты штрафа
     */
    @Column(name = "pay_date")
    private LocalDateTime payDate;
    /**
     * сумма штрафа
     */
    @Column(name = "amount")
    private int amount;
    /**
     * выплаченная сумма штрафа
     */
    @Column(name = "amount_complete")
    private int completeAmount;
    /**
     * аренда
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "rent_id", unique = true, nullable = false)
    private Rent rent;
    @ManyToOne
    @JoinColumn(name = "card_id", unique = true, nullable = false)
    private Card card;

    public Penalty() {
    }

    /**
     * конструктор
     * @param id айди штрафа
     * @param reason причина штрафа
     * @param date дата штрафа
     * @param amount сумма штрафа
     * @param completeAmount выплаченная сумма штрафа
     * @param rent аренда
     */
    public Penalty(int id, String reason, LocalDateTime date, LocalDateTime payDate,
                   int amount, int completeAmount, Rent rent, Card card) {
        this.id = id;
        this.reason = reason;
        this.date = date;
        this.payDate = payDate;
        this.amount = amount;
        this.completeAmount = completeAmount;
        this.rent = rent;
        this.card = card;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public LocalDateTime getPayDate() {
        return payDate;
    }

    public void setPayDate(LocalDateTime payDate) {
        this.payDate = payDate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getCompleteAmount() {
        return completeAmount;
    }

    public void setCompleteAmount(int completeAmount) {
        this.completeAmount = completeAmount;
    }

    public Rent getRent() {
        return rent;
    }

    public void setRent(Rent rent) {
        this.rent = rent;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Penalty penalty = (Penalty) o;
        return getAmount() == penalty.getAmount() &&
                getReason().equals(penalty.getReason()) &&
                getDate().equals(penalty.getDate()) &&
                Objects.equals(getPayDate(), penalty.getPayDate()) &&
                getRent().equals(penalty.getRent()) &&
                getCard().equals(penalty.getCard());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getReason(), getDate(), getPayDate(), getAmount(), getRent(), getCard());
    }

    @Override
    public String toString() {
        return "Penalty{" +
                "id=" + id +
                ", reason='" + reason + '\'' +
                ", date=" + date +
                ", payDate=" + payDate +
                ", amount=" + amount +
                ", completeAmount=" + completeAmount +
                ", rent=" + rent +
                ", card=" + card +
                '}';
    }
}
