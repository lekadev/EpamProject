package com.epam.mylibrary.entity;

import java.util.Objects;

public class Author extends Entity {

    private String nameFirst;
    private String nameLast;

    public Author() {}

    public Author(String nameFirst, String nameLast) {
        this.nameFirst = nameFirst;
        this.nameLast = nameLast;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(nameFirst, author.nameFirst) &&
                Objects.equals(nameLast, author.nameLast);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameFirst, nameLast);
    }

    @Override
    public String toString() {
        return nameFirst + " " + nameLast;
    }
}