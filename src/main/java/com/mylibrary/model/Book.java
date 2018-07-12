package com.mylibrary.model;

import java.util.List;

public class Book {

    private int id;
    private String title;
    private List<Author> authors;
    private String publisher;
    private int numberCopies;

    public Book() {

    }

    public Book(int id, String title, List<Author> authors, String publisher, int numberCopies) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.publisher = publisher;
        this.numberCopies = numberCopies;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public static class Author {

        private int id;
        private String nameFirst;
        private String nameLast;

        public Author() {

        }

        public Author(int id, String name, String surname) {
            this.id = id;
            this.nameFirst = name;
            this.nameLast = surname;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        @Override
        public String toString() {
            return "Author ID#: " + id +
                    "\nName: " + nameFirst + nameLast;
        }
    }

    @Override
    public String toString() {
        return "Book ID#: " + id +
                "\nTitle: " + title +
                "\nAuthors: " + authors +
                "\nPublisher: " + publisher +
                "\nAvailable copies: " + numberCopies;
    }
}