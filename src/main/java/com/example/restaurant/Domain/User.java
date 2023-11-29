package com.example.restaurant.Domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;


@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "login", length = 225, nullable = false, unique = true)
    @NotBlank(message = "You must input login")
    private String login;

    @Column(name = "password_hash", length = 225, nullable = false)
    @NotBlank(message = "You must input password")
    private String password_hash;
    //    @Transient
//    @NotBlank(message = "You must confirm password")
//    private String password_hash2;
    @Column(name = "created_at", nullable = true)
    private long created_at;
    @Column(name = "updated_at", nullable = true)
    private long updated_at;
    @Column(name = "last_login", nullable = true)
    private long last_login;

    public User() {
    }

    public User(String login, String password_hash, long created_at) {
        this.login = login;
        this.password_hash = password_hash;
        this.created_at = created_at;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword_hash() {
        return password_hash;
    }

    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }

    public long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }

    public long getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(long updated_at) {
        this.updated_at = updated_at;
    }

    public long getLast_login() {
        return last_login;
    }

    public void setLast_login(long last_login) {
        this.last_login = last_login;
    }
}
