package com.mylibrary.action.post;

import com.mylibrary.action.*;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import com.mylibrary.model.Author;
import com.mylibrary.dao.AuthorDao;
import com.mylibrary.db.ConnectionPool;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mylibrary.validator.InputValidator;
import com.mylibrary.dao.exception.DaoException;
import com.mylibrary.action.exception.ActionException;

public class AddAuthorAction implements Action {

    private final static Logger logger = Logger.getLogger(AddAuthorAction.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        String nameFirst = req.getParameter(Parameters.AUTHOR_NAME);
        String nameLast = req.getParameter(Parameters.AUTHOR_SURNAME);
        boolean fieldsValid = InputValidator.isTextValid(nameFirst) && InputValidator.isTextValid(nameLast);
        if(!fieldsValid) {
            req.getSession().setAttribute(Attributes.AUTHOR_ADD_MESSAGE, ErrorMessages.TEXT_INPUT_ERROR);
            return Paths.REDIRECT_AUTHOR_FORM;
        }
        Author newAuthor = new Author();
        newAuthor.setNameFirst(nameFirst);
        newAuthor.setNameLast(nameLast);
        ConnectionPool pool = ConnectionPool.getInstance();
        AuthorDao authorDao = new AuthorDao(pool);
        try {
            authorDao.create(newAuthor);
        } catch (DaoException e) {
            throw new ActionException();
        }
        req.getSession().setAttribute(Attributes.AUTHOR_ADD_MESSAGE, ErrorMessages.AUTHOR_ADD_SUCCESS);
        logger.log(Level.INFO, "New author was added: " + newAuthor.toString());
        return Paths.REDIRECT_AUTHOR_FORM;
    }
}
