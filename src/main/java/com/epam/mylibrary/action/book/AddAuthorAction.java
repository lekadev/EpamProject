package com.epam.mylibrary.action.book;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import com.epam.mylibrary.action.Action;
import com.epam.mylibrary.entity.Author;
import com.epam.mylibrary.dao.AuthorDao;
import com.epam.mylibrary.constants.Const;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.epam.mylibrary.dao.exception.DaoException;
import com.epam.mylibrary.action.exception.ActionException;
import static com.epam.mylibrary.validator.FormValidator.isAuthorFormValid;

public class AddAuthorAction implements Action {

    private final static Logger logger = Logger.getLogger(AddAuthorAction.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        if (!isAuthorFormValid(req)) {
            return Const.REDIRECT_AUTHOR_FORM;
        }
        String nameFirst = req.getParameter(Const.PARAM_NAME);
        String nameLast = req.getParameter(Const.PARAM_SURNAME);
        Author author = new Author();
        author.setNameFirst(nameFirst);
        author.setNameLast(nameLast);
        AuthorDao authorDao = new AuthorDao();
        try {
            authorDao.create(author);
        } catch (DaoException e) {
            throw new ActionException();
        }
        req.getSession().setAttribute(Const.AUTHOR_ADD_MESSAGE, Const.AUTHOR_ADD_SUCCESS);
        logger.log(Level.INFO, "New author was added: " + author.toString());
        return Const.REDIRECT_AUTHOR_FORM;
    }
}
