package ru.ancndz.libraryBase.content.operations;

import ru.ancndz.libraryBase.content.entity.User;
import ru.ancndz.libraryBase.content.libraryEnvironment.Book;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "reservations")
public class Reservations {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class, cascade = CascadeType.DETACH)
    @JoinColumn(name = "card_id")
    private User user;

    @Transient
    private int client_id;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Book.class, cascade = CascadeType.DETACH)
    @JoinColumn(name = "book_id")
    private Book book;

    @Transient
    private int book_id;

    public Reservations() {
    }

    public Reservations(int id, LocalDateTime endDate, User user, Book book) {
        this.id = id;
        this.endDate = endDate;
        this.user = user;
        this.book = book;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservations that = (Reservations) o;
        return getEndDate().equals(that.getEndDate()) &&
                getUser().equals(that.getUser()) &&
                getBook().equals(that.getBook());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEndDate(), getUser(), getBook());
    }

    @Override
    public String toString() {
        return "Reservations{" +
                "id=" + id +
                ", endDate=" + endDate +
                ", user=" + user +
                ", book=" + book +
                '}';
    }
}
