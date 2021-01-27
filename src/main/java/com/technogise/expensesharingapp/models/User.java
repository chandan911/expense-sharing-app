package com.technogise.expensesharingapp.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Entity
@Table(name = "users", indexes = {@Index(name = "i_phone_number_unique", columnList = "phone_number", unique = true)})
public class User extends BasePersistenceModel {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "password", nullable = false)
    @JsonProperty(access = WRITE_ONLY)
    private String password;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    public User() {
    }

    public User(String name, String password, String phoneNumber) {
        this.name = name;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getName().equals(user.getName()) &&
            getPhoneNumber().equals(user.getPhoneNumber()) &&
            getPassword().equals(user.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getPhoneNumber(), getPassword());
    }
}
