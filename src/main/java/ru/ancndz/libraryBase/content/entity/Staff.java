package ru.ancndz.libraryBase.content.entity;

import org.springframework.security.core.GrantedAuthority;
import ru.ancndz.libraryBase.content.jobs.Job;
import ru.ancndz.libraryBase.content.libraryEnvironment.Library;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

/**
 * Класс сотрудника
 */
@Entity
@Table
public class Staff extends LibraryUser {
    /*@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;*/
    /**
     * адрес проживания
     */
    @Column
    private String address;
    /**
     * тел. номер (без +)
     */
    @Column
    private int number;
    /**
     * должность
     */
    @ManyToOne(targetEntity = Job.class)
    @JoinColumn(nullable = false)
    private Job job;

    @Transient
    private int job_id;

    /**
     * библиотека, в которой сотрудник работает
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = Library.class)
    @JoinColumn(nullable = false)
    private Library library;

    @Transient
    private int library_id;


    public Staff() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
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
        if (!(o instanceof Staff)) return false;
        if (!super.equals(o)) return false;
        Staff staff = (Staff) o;
        return number == staff.number &&
                job_id == staff.job_id &&
                library_id == staff.library_id &&
                Objects.equals(address, staff.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), address, number, job_id, library_id);
    }

    @Override
    public String toString() {
        return "Staff{" +
                "address='" + address + '\'' +
                ", number=" + number +
                ", job=" + job +
                ", library=" + library +
                '}';
    }
}
