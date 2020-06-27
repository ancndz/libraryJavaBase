package ru.ancndz.libraryBase.content.operations;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.Objects;


@Entity
@Table
public class Penalty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column
    @NotBlank
    private String reason;

    @Column
    @PastOrPresent
    private LocalDateTime date;

    @Column
    private LocalDateTime payDate;

    @Column
    @Positive
    private int amount;

    @Column
    @Positive
    private int completeAmount;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    private Rent rent;

    public Penalty() {
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Penalty penalty = (Penalty) o;
        return getAmount() == penalty.getAmount() &&
                getCompleteAmount() == penalty.getCompleteAmount() &&
                Objects.equals(getReason(), penalty.getReason()) &&
                getDate().equals(penalty.getDate()) &&
                Objects.equals(getPayDate(), penalty.getPayDate()) &&
                getRent().equals(penalty.getRent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getReason(), getDate(), getPayDate(), getAmount(), getCompleteAmount(), getRent());
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
                '}';
    }
}
