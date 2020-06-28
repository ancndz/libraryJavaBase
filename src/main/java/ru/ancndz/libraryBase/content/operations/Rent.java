package ru.ancndz.libraryBase.content.operations;

import ru.ancndz.libraryBase.content.entity.LibraryUser;
import ru.ancndz.libraryBase.content.entity.Staff;
import ru.ancndz.libraryBase.content.libraryEnvironment.Book;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Класс аренды
 */
@Entity
@Table
public class Rent {
    /**
     * айди аренды
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    /**
     * дата начала аренды
     */
    @Column
    @PastOrPresent
    private LocalDateTime startDate;

    @Column
    @Future
    private LocalDateTime endDate;

    @Column
    private LocalDateTime factEndDate;

    @OneToOne
    @JoinColumn(unique = true, nullable = false)
    private Staff staff;

    @OneToOne()
    @JoinColumn(unique = true, nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn
    private LibraryUser libraryUser;

    public Rent() {}

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

    public LibraryUser getLibraryUser() {
        return libraryUser;
    }

    public void setLibraryUser(LibraryUser libraryUser) {
        this.libraryUser = libraryUser;
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
                getLibraryUser().equals(rent.getLibraryUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStartDate(), getEndDate(), getFactEndDate(), getStaff(), getBook(), getLibraryUser());
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
                ", user=" + libraryUser +
                '}';
    }
}
