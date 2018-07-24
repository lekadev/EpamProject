package com.epam.mylibrary.entity;

import java.util.Date;
import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(user, order.user) &&
                Objects.equals(book, order.book) &&
                status == order.status &&
                Objects.equals(date, order.date);
    }

    @Override
    public int hashCode() {

        return Objects.hash(user, book, status, date);
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