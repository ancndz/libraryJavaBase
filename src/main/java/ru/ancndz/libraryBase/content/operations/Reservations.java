package ru.ancndz.libraryBase.content.operations;

import ru.ancndz.libraryBase.content.entity.UserExtras;
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

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = UserExtras.class, cascade = CascadeType.DETACH)
    @JoinColumn(name = "card_id")
    private UserExtras userExtras;

    @Transient
    private int user_id;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Book.class, cascade = CascadeType.DETACH)
    @JoinColumn(name = "book_id")
    private Book book;

    @Transient
    private int book_id;

    public Reservations() {
    }

    public Reservations(int id, LocalDateTime endDate, UserExtras userExtras, Book book) {
        this.id = id;
        this.endDate = endDate;
        this.userExtras = userExtras;
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

    public UserExtras getUserExtras() {
        return userExtras;
    }

    public void setUserExtras(UserExtras userExtras) {
        this.userExtras = userExtras;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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
                getUserExtras().equals(that.getUserExtras()) &&
                getBook().equals(that.getBook());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEndDate(), getUserExtras(), getBook());
    }

    @Override
    public String toString() {
        return "Reservations{" +
                "id=" + id +
                ", endDate=" + endDate +
                ", userExtras=" + userExtras +
                ", book=" + book +
                '}';
    }
}
