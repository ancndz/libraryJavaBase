package ru.ancndz.libraryBase.content.operations;

import ru.ancndz.libraryBase.content.entity.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;


@Entity
@Table(name = "penalty")
public class Penalty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "reason")
    private String reason;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "pay_date")
    private LocalDateTime payDate;

    @Column(name = "amount")
    private int amount;

    @Column(name = "amount_complete")
    private int completeAmount;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "rent_id", unique = true, nullable = false)
    private Rent rent;

    @ManyToOne
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;

    public Penalty() {
    }

    public Penalty(int id, String reason, LocalDateTime date, LocalDateTime payDate,
                   int amount, int completeAmount, Rent rent, User user) {
        this.id = id;
        this.reason = reason;
        this.date = date;
        this.payDate = payDate;
        this.amount = amount;
        this.completeAmount = completeAmount;
        this.rent = rent;
        this.user = user;
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
        Penalty penalty = (Penalty) o;
        return getAmount() == penalty.getAmount() &&
                getReason().equals(penalty.getReason()) &&
                getDate().equals(penalty.getDate()) &&
                Objects.equals(getPayDate(), penalty.getPayDate()) &&
                getRent().equals(penalty.getRent()) &&
                getUser().equals(penalty.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getReason(), getDate(), getPayDate(), getAmount(), getRent(), getUser());
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
                ", user=" + user +
                '}';
    }
}
