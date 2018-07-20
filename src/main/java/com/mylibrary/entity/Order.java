package com.mylibrary.entity;

import java.util.Date;

public class Order extends Entity {

    private User user;
    private Book book;
    private OrderStatus status;
    private Date date;

    public Order() {}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public enum OrderStatus {
        PENDING,
        RETURNED,
        LONG_LOAN,
        SHORT_LOAN,
        DENIED
    }

    @Override
    public String toString() {
        return "Order ID#" + super.getId() +
                "\n" + user.toString() +
                "\n" + book.toString() +
                "\nStatus: " + status.toString() +
                "\nOrdered on : " + date;
    }
}