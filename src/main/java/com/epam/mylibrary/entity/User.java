package com.epam.mylibrary.entity;

import java.util.Objects;

public class User extends Entity {

    private String email;
    private String password;
    private String nameFirst;
    private String nameLast;
    private Role role;

    public User() {}

    public User(int id, String email, String password, String nameFirst, String nameLast, Role role) {
        super(id);
        this.email = email;
        this.password = password;
        this.nameFirst = nameFirst;
        this.nameLast = nameLast;
        this.role = role;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public enum Role {
        LIBRARIAN, READER, GUEST
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                Objects.equals(nameFirst, user.nameFirst) &&
                Objects.equals(nameLast, user.nameLast) &&
                role == user.role;
    }

    @Override
    public int hashCode() {

        return Objects.hash(email, password, nameFirst, nameLast, role);
    }

    @Override
    public String toString() {
        return "User ID#: " + super.getId() +
                "\nEmail: " + email +
                "\nName: " + nameFirst + " " + nameLast +
                "\nRole: " + role;
    }
}
