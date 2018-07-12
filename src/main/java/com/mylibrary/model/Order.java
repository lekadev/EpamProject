package com.mylibrary.model;

import java.util.Date;

public class Order {

    private int id;
    private User user;
    private Book book;
    private OrderStatus status;
    private Date date;

    public Order() {

    }

    public Order(int id, User user, Book book, OrderStatus status, Date date) {
        this.id = id;
        this.user = user;
        this.book= book;
        this.status = status;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
        PENDING(1),
        RETURNED(2),
        LONG_LOAN(3),
        SHORT_LOAN(4),
        DENIED(5);

        int id;
        String description;

        OrderStatus() {

        }

        OrderStatus(int id) {
            this.id = id;
        }

        OrderStatus(int id, String description) {
            this.id = id;
            this.description = description;
        }

        public int getId() {
            return id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    @Override
    public String toString() {
        return "Order ID#" + id +
                "\n" + user.toString() +
                "\n" + book.toString() +
                //"\nStatus: " + status.getDescription() +
                "\nOrdered on : " + date;
    }
}