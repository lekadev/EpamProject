package com.mylibrary.tag;

import com.mylibrary.model.Author;

import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.List;
import java.util.StringJoiner;

public class DisplayAuthorsTag extends SimpleTagSupport {

    private List<Author> authorsList;

    public void doTag() throws IOException {
        StringJoiner authors = new StringJoiner(",");
        for(Author author : authorsList) {
            authors.add(author.toString());
        }
        getJspContext().getOut().println(authors);
    }

    public void setAuthorsList(List<Author> authorsList) {
        this.authorsList = authorsList;
    }
}
