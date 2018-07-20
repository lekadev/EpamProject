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
        Author other = (Author)object;
        if(this.getId() != other.getId()) {
            return false;
        }
        if(nameFirst == null) {
            if(other.getNameFirst() != null) {
                return false;
            }
        } else if(!nameFirst.equals(other.nameFirst)) {
            return false;
        }
        if(nameLast == null) {
            return other.getNameLast() == null;
        } else return nameLast.equals(other.nameLast);
    }

    @Override
    public String toString() {
        return nameFirst + " " + nameLast;
    }
}