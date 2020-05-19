package ru.ancndz.libraryBase.content.operations;

import ru.ancndz.libraryBase.content.libraryEnvironment.Book;
import ru.ancndz.libraryBase.content.libraryEnvironment.Card;
import ru.ancndz.libraryBase.content.libraryEnvironment.Staff;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Класс аренды
 */
@Entity
@Table(name = "rent")
public class Rent {
    /**
     * айди аренды
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    /**
     * дата начала аренды
     */
    @Column(name = "start_date")
    private LocalDateTime startDate;
    /**
     * дата конца аренды
     */
    @Column(name = "end_date")
    private LocalDateTime endDate;
    /**
     * фактическое окончание аренды (книга возвращена)
     */
    @Column(name = "fact_end_date")
    private LocalDateTime factEndDate;
    /**
     * сотрудник, выдавшего книгу
     */
    @OneToOne
    @JoinColumn(name = "staff_id", unique = true, nullable = false)
    private Staff staff;
    /**
     * книга
     */
    @OneToOne()
    @JoinColumn(name = "book_id", unique = true, nullable = false)
    private Book book;
    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;

    public Rent() {}

    /**
     * конструктор
     * @param id айди аренды
     * //@param dateStart дата начала аренды
     * //@param endDate дата завершения аренды
     * @param factEndDate дата фактического завершения аренда (книга возвращена в библиотеку)
     * @param staff сотрудник
     * @param book книга
     */
    public Rent(int id, LocalDateTime startDate, LocalDateTime endDate,
                LocalDateTime factEndDate, Staff staff, Book book, Card card) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.factEndDate = factEndDate;
        this.staff = staff;
        this.book = book;
        this.card = card;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getFactEndDate() {
        return factEndDate;
    }

    public void setFactEndDate(LocalDateTime factEndDate) {
        this.factEndDate = factEndDate;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
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
        Rent rent = (Rent) o;
        return getStartDate().equals(rent.getStartDate()) &&
                getEndDate().equals(rent.getEndDate()) &&
                Objects.equals(getFactEndDate(), rent.getFactEndDate()) &&
                getStaff().equals(rent.getStaff()) &&
                getBook().equals(rent.getBook()) &&
                getCard().equals(rent.getCard());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStartDate(), getEndDate(), getFactEndDate(), getStaff(), getBook(), getCard());
    }

    @Override
    public String toString() {
        return "Rent{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", factEndDate=" + factEndDate +
                ", staff=" + staff +
                ", book=" + book +
                ", card=" + card +
                '}';
    }
}
