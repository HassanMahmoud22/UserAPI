package com.example.entities;

import com.example.security.HashCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;
import java.security.NoSuchAlgorithmException;


@Entity
@Table(name = "users")
public class User {

    @Id
    private String id;
    @NotNull
    @Column(nullable = false, length = 20)
    private String firstName;
    @NotNull
    @Column(nullable = false, length = 20)
    private String lastName;
    @NotNull
    @Column(nullable = false, length = 50)
    private String email;
    @NotNull
    @Column(nullable = false, length = 20)
    private boolean marketingConsent;

    public  User(){};

    public User(String firstName, String lastName, String email, boolean marketingConsent) throws NoSuchAlgorithmException {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.marketingConsent = marketingConsent;
        this.id = HashCreator.encryptSHA1Hash(email);
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isMarketingConsent() {
        return marketingConsent;
    }

    public void setMarketingConsent(boolean marketingConsent) {
        this.marketingConsent = marketingConsent;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":\"" + id + '\"' +
                ", \"firstName\":\"" + firstName + '\"' +
                ", \"lastName\":\"" + lastName + '\"' +
                ", \"email\":\"" + email + '\"' +
                ", \"marketingConsent\":" + marketingConsent +
                '}';
    }
}