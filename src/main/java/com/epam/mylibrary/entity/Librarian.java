package com.epam.mylibrary.entity;

public class Librarian extends User {

    private String numberPhone;

    public Librarian() {}

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
