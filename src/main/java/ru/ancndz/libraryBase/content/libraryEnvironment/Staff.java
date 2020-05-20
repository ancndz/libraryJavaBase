package ru.ancndz.libraryBase.content.libraryEnvironment;

import ru.ancndz.libraryBase.content.jobs.Job;

import javax.persistence.*;
import java.util.Objects;

/**
 * Класс сотрудника
 */
@Entity
@Table(name = "staff")
public class Staff {
    /**
     * айди работника
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    /**
     * имя работника
     */
    @Column(name = "first_name")
    private String firstName;
    /**
     * фамилия работника
     */
    @Column(name = "last_name")
    private String lastName;
    /**
     * адрес проживания
     */
    @Column(name = "address")
    private String address;
    /**
     * тел. номер (без +)
     */
    @Column(name = "number")
    private int number;
    /**
     * должность
     */
    @ManyToOne(targetEntity = Job.class)
    @JoinColumn(name = "job_id", unique = true, nullable = false)
    private Job job;

    @Transient
    private int job_id;

    /**
     * библиотека, в которой сотрудник работает
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = Library.class)
    @JoinColumn(name = "library_id")
    private Library library;

    @Transient
    private int library_id;

    public Staff() {
    }

    /**
     * конструктор
     * @param id айди работника
     * @param firstName имя работника
     * @param secondName фамилия работника
     * @param address адрес проживания
     * @param number тел. номер (без +)
     * @param job должность
     */
    public Staff(int id, String firstName, String secondName, String address, int number, Job job) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = secondName;
        this.address = address;
        this.number = number;
        this.job = job;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

    public int getJob_id() {
        return job_id;
    }

    public void setJob_id(int job_id) {
        this.job_id = job_id;
    }

    public int getLibrary_id() {
        return library_id;
    }

    public void setLibrary_id(int library_id) {
        this.library_id = library_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Staff staff = (Staff) o;
        return getNumber() == staff.getNumber() &&
                getFirstName().equals(staff.getFirstName()) &&
                getLastName().equals(staff.getLastName()) &&
                Objects.equals(getAddress(), staff.getAddress()) &&
                Objects.equals(getJob(), staff.getJob()) &&
                Objects.equals(getLibrary(), staff.getLibrary());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName(), getAddress(), getNumber(), getJob(), getLibrary());
    }

    @Override
    public String toString() {
        return "Staff{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", number=" + number +
                ", job=" + job +
                ", library=" + library +
                '}';
    }
}
