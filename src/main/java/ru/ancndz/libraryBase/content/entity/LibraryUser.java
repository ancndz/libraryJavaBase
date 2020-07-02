package ru.ancndz.libraryBase.content.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.ancndz.libraryBase.content.jobs.Role;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table
public class LibraryUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String password;

    @Transient
    private String passwordConfirm;

    @Email
    private String email;

    @OneToOne(fetch = FetchType.LAZY, targetEntity = UserExtras.class, cascade = CascadeType.ALL)
    @JoinColumn(unique = true, nullable = false)
    @NotNull
    private UserExtras userExtras;

    @Transient
    private int userExtrasId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            joinColumns = @JoinColumn,
            inverseJoinColumns = @JoinColumn)
    private Set<Role> roles;

    public LibraryUser() {
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserExtras getUserExtras() {
        return userExtras;
    }

    public void setUserExtras(UserExtras userExtras) {
        this.userExtras = userExtras;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUserExtrasId() {
        return userExtrasId;
    }

    public void setUserExtrasId(int userExtrasId) {
        this.userExtrasId = userExtrasId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LibraryUser libraryUser = (LibraryUser) o;
        return getPassword().equals(libraryUser.getPassword()) &&
                email.equals(libraryUser.email) &&
                getUserExtras().equals(libraryUser.getUserExtras());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPassword(), email);
    }

    @Override
    public String toString() {
        return "LibraryUser{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", userExtras=" + userExtras +
                ", userExtrasId=" + userExtrasId +
                ", roles=" + roles +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
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
}
