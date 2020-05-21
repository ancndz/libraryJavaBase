package ru.ancndz.libraryBase.content.libraryEnvironment;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.ancndz.libraryBase.content.jobs.Job;
import ru.ancndz.libraryBase.content.jobs.Role;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

/**
 * Класс сотрудника
 */
@Entity
@Table(name = "staff")
public class Staff implements UserDetails {
    /**
     * айди работника
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Transient
    private String passwordConfirm;
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
    @JoinColumn(name = "jobs_id", unique = true, nullable = false)
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "roles_has_staff",
            joinColumns = @JoinColumn(name = "staff_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id"))
    private Set<Role> roles;

    public Staff() {
    }

    public Staff(int id, String password, String firstName, String secondName, String address, int number, Job job) {
        this.id = id;
        this.password = password;
        this.firstName = firstName;
        this.lastName = secondName;
        this.address = address;
        this.number = number;
        this.job = job;
    }

    public boolean passwordConfirms() {
        return this.password.equals(this.passwordConfirm);
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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

    public void setPassword(String password) {
        this.password = password;
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

    public boolean passwordsCheck() {
        return this.password.equals(this.passwordConfirm);
    }
}
