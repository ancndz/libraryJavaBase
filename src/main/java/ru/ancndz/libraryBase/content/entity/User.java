package ru.ancndz.libraryBase.content.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.ancndz.libraryBase.content.jobs.Role;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "password")
    private String password;

    @Transient
    private String passwordConfirm;

    @Column(name = "email")
    private String email;

    @OneToOne(fetch = FetchType.LAZY, targetEntity = UserExtras.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_extras_id", unique = true, nullable = false)
    private UserExtras userExtras;

    @Transient
    private int userExtrasId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "roles_has_user",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id"))
    private Set<Role> roles;


    public User(){
    }

    public User(int id, String password, UserExtras userExtras) {
        this.id = id;
        this.password = password;
        this.userExtras = userExtras;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public boolean passwordsCheck() {
        return this.password.equals(this.passwordConfirm);
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
        User user = (User) o;
        return getPassword().equals(user.getPassword()) &&
                email.equals(user.email) &&
                getUserExtras().equals(user.getUserExtras());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPassword(), email);
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
