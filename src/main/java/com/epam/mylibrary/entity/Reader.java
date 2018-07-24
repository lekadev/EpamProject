package com.epam.mylibrary.entity;

import java.util.Date;

public class Reader extends User {

    private Date dateRegistered;

    public Reader() {}

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
