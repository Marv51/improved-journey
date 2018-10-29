package de.hska.vis.webshop.core.database.model.impl;

import de.hska.vis.webshop.core.database.model.IUser;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "customer")
public class User implements IUser, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    // dumb column unused by us and always the same value as "firstname" ... should be
    // therefore only here to support the legacy format and that no exceptions occur because it can't be null
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "firstname", nullable = false)
    private String firstname;

    @Column(name = "lastname", nullable = false)
    private String lastname;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role", nullable = false)
    private Role role;

    public User() {
        // as default value, a value that is negative as signal that it's not saved in the database
        this.id = -1;
    }

    public User(String username, String firstname,
                String lastname, String password, Role role) {
        this();
        this.username = username;
        this.firstname = firstname;
        this.name = firstname;
        this.lastname = lastname;
        this.password = password;
        this.role = role;
    }

    public User(int id, String username, String firstname,
                String lastname, String password, Role role) {
        this.id = id;
        this.username = username;
        this.firstname = firstname;
        this.name = firstname;
        this.lastname = lastname;
        this.password = password;
        this.role = role;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getFirstname() {
        return firstname;
    }

    @Override
    public void setFirstname(String firstname) {
        this.firstname = firstname;
        this.name = firstname;
    }

    @Override
    public String getLastname() {
        return lastname;
    }

    @Override
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Role getRole() {
        return role;
    }

    @Override
    public void setRole(Role role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.firstname = name;
    }
}
