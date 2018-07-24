package com.epam.mylibrary.entity;

import java.util.List;
import java.util.Objects;

public class Book extends Entity {

    private String title;
    private List<Author> authors;
    private String publisher;
    private int numberCopies;

    public Book() {}

    public Book(String title, List<Author> authors, String publisher, int numberCopies) {
        this.title = title;
        this.authors = authors;
        this.publisher = publisher;
        this.numberCopies = numberCopies;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getNumberCopies() {
        return numberCopies;
    }

    public void setNumberCopies(int numberCopies) {
        this.numberCopies = numberCopies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return numberCopies == book.numberCopies &&
                Objects.equals(title, book.title) &&
                Objects.equals(authors, book.authors) &&
                Objects.equals(publisher, book.publisher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, authors, publisher, numberCopies);
    }

    @Override
    public String toString() {
        return "Book ID#: " + super.getId() +
                "\nTitle: " + title +
                "\nAuthors: " + authors.toString() +
                "\nPublisher: " + publisher +
                "\nAvailable copies: " + numberCopies;
    }
}