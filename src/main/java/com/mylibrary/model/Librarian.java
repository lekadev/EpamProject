package com.mylibrary.model;

public class Librarian extends User {

    private String numberPhone;

    public Librarian() {

    }

    public Librarian(int id, String email, String password, String nameFirst, String nameLast, Role role, String numberPhone) {
        super(id, email, password, nameFirst, nameLast, role);
        this.numberPhone = numberPhone;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nPhone: " + numberPhone;
    }
}
