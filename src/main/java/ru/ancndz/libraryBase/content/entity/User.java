package ru.ancndz.libraryBase.content.entity;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Класс клиента
 */
@Entity
@Table(name = "client")
public class User {
    /**
     * айди клиента
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    /**
     * имя клиента
     */
    @Column(name = "first_name")
    private String firstName;
    /**
     * фамилия клиента
     */
    @Column(name = "last_name")
    private String lastName;
    /**
     * дата регистрации
     */
    @Column(name = "date_reg")
    private LocalDateTime dateReg;
    /**
     * e-mail клиента
     */
    @Column(name = "email")
    private String email;
    /**
     * студент? науч работник?
     */
    @Column(name = "status")
    private String status;

    public User(){
        this.dateReg = LocalDateTime.now();
    }

    public User(int id, String firstName, String lastName, String email, String status) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateReg = LocalDateTime.now();
        this.email = email;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDateTime getDateReg() {
        return dateReg;
    }

    public void setDateReg(LocalDateTime dateReg) {
        this.dateReg = dateReg;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getFirstName().equals(user.getFirstName()) &&
                getLastName().equals(user.getLastName()) &&
                Objects.equals(getEmail(), user.getEmail()) &&
                getStatus().equals(user.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName(), getEmail(), getStatus());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateReg=" + dateReg +
                ", email='" + email + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
