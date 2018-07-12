package com.mylibrary.model;

public class User {

    private int id;
    private String email;
    private String password;
    private String nameFirst;
    private String nameLast;
    private Role role;

    public User() {

    }

    public User(int id, String email, String password, String nameFirst, String nameLast, Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nameFirst = nameFirst;
        this.nameLast = nameLast;
        this.role = role;
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
        LIBRARIAN, READER, GUEST;
    }

    @Override
    public String toString() {
        return "User ID#: " + id +
                "\nEmail: " + email +
                "\nName: " + nameFirst + " " + nameLast +
                "\nRole: " + role;
    }
}
