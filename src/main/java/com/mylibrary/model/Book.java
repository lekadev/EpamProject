package com.mylibrary.model;

import java.util.List;

public class Book extends Entity {

    private String title;
    private List<Author> authors;
    private String publisher;
    private int numberCopies;

    public Book() {}

    public Book(int id, String title, List<Author> authors, String publisher, int numberCopies) {
        super(id);
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
    public boolean equals(Object object) {
        if(this == object) {
            return true;
        }
        if(object == null) {
            return false;
        }
        if(getClass() != object.getClass()) {
            return false;
        }
        Book other = (Book) object;
        if(this.getId() != other.getId()) {
            return false;
        }
        if(this.numberCopies != other.numberCopies) {
            return false;
        }
        if(title == null) {
            if(other.title != null) {
                return false;
            }
        } else if(!title.equals(other.title)) {
            return false;
        }
        if(publisher == null) {
            if(other.publisher != null) {
                return false;
            }
        } else if(!publisher.equals(other.publisher)) {
            return false;
        }
        if(authors == null) {
            return other.authors == null;
        } else return authors.equals(other.authors);
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