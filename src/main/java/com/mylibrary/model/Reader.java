package com.mylibrary.model;

import java.util.Date;

public class Reader extends User {

    private Date dateRegistered;

    public Reader() {

    }

    public Reader(int id, String email, String password, String nameFirst, String nameLast, Role role, Date date) {
        super(id, email, password, nameFirst, nameLast, role);
        this.dateRegistered = date;
    }

    public Date getDateRegistered() {
        return dateRegistered;
    }

    public void setDateRegistered(Date dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nRegistered on: " + dateRegistered;
    }
}
