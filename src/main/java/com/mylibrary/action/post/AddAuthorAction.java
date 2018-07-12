package com.mylibrary.action.post;

import java.util.List;
import com.mylibrary.action.*;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import com.mylibrary.model.Book;
import com.mylibrary.dao.AuthorDao;
import com.mylibrary.db.ConnectionPool;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddAuthorAction implements Action {

    private final static Logger logger = Logger.getLogger(AddAuthorAction.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String path = Paths.SHOW_AUTHOR_FORM;
        String nameFirst = req.getParameter("nameFirst");
        String nameLast = req.getParameter("nameLast");
        req.getSession().removeAttribute(Attributes.AUTHOR_ADD_MESSAGE);
        boolean fieldsValid = InputValidator.validateInputField(nameFirst) && InputValidator.validateInputField(nameLast);
        if(!fieldsValid) {
            req.getSession().setAttribute(Attributes.AUTHOR_ADD_MESSAGE, ErrorMessages.TEXT_INPUT_ERROR);
            return path;
        }
        Book.Author author = new Book.Author();
        author.setNameFirst(nameFirst);
        author.setNameLast(nameLast);
        ConnectionPool pool = ConnectionPool.getInstance();
        int id = new AuthorDao(pool).create(author);
        if(id != 0) {
            List<Book.Author> allAuthors = new AuthorDao(pool).findAll();
            if(allAuthors != null) {
                req.getServletContext().setAttribute(Attributes.ALL_AUTHORS, allAuthors);
            }
            req.getSession().setAttribute(Attributes.AUTHOR_ADD_MESSAGE, ErrorMessages.AUTHOR_SUCCESS_MESSAGE);
            logger.log(Level.INFO, "New author was added: " + author.toString());
        } else {
            req.getSession().setAttribute(Attributes.AUTHOR_ADD_MESSAGE, ErrorMessages.AUTHOR_ADD_ERROR);
        }
        return path;
    }
}
