package com.epam.mylibrary.entity;

import java.util.Date;
import java.util.Objects;

public class User extends Entity {

    private String email;
    private String password;
    private UserRole role;
    private String nameFirst;
    private String nameLast;
    private Date dateRegistered;

    public User() {}

    public User(int id, String email, String password, UserRole role, String nameFirst, String nameLast, Date date) {
        super(id);
        this.email = email;
        this.password = password;
        this.role = role;
        this.nameFirst = nameFirst;
        this.nameLast = nameLast;
        this.dateRegistered = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getNameFirst() {
        return nameFirst;
    }

    public void setNameFirst(String nameFirst) {
        this.nameFirst = nameFirst;
    }

    public String getNameLast() {
        return nameLast;
    }

    public void setNameLast(String nameLast) {
        this.nameLast = nameLast;
    }

    public Date getDateRegistered() {
        return dateRegistered;
    }

    public void setDateRegistered(Date dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                role == user.role &&
                Objects.equals(nameFirst, user.nameFirst) &&
                Objects.equals(nameLast, user.nameLast) &&
                Objects.equals(dateRegistered, user.dateRegistered);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password, role, nameFirst, nameLast, dateRegistered);
    }

    @Override
    public String toString() {
        return "User ID#: " + super.getId() +
                "\nRole: " + role +
                "\nEmail: " + email +
                "\nName: " + nameFirst + " " + nameLast +
                "\nRegistered on: " + dateRegistered;
    }
}
