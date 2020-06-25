package ru.ancndz.libraryBase.content.jobs;

import org.springframework.security.core.GrantedAuthority;
import ru.ancndz.libraryBase.content.entity.LibraryUser;
import ru.ancndz.libraryBase.content.entity.Staff;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table
public class Role implements GrantedAuthority {

    @Id
    @Column
    private int id;
    @Column
    private String name;

    @Transient
    @ManyToMany
    private Set<LibraryUser> libraryUsers;

    @Transient
    @ManyToMany
    private Set<Staff> staff;

    @Override
    public String getAuthority() {
        return name;
    }

    public Role() {
    }

    public Role(int id) {
        this.id = id;
    }

    public Role(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<LibraryUser> getLibraryUsers() {
        return libraryUsers;
    }

    public void setLibraryUsers(Set<LibraryUser> libraryUsers) {
        this.libraryUsers = libraryUsers;
    }

    public Set<Staff> getStaff() {
        return staff;
    }

    public void setStaff(Set<Staff> staff) {
        this.staff = staff;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return getName().equals(role.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
