package com.mylibrary.model;

public class Author extends Entity {

    private String nameFirst;
    private String nameLast;

    public Author() {}

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
        return nameFirst + " " + nameLast;
    }
}