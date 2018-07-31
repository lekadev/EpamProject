package com.epam.mylibrary.action.book;
;
import com.epam.mylibrary.entity.Author;
import com.epam.mylibrary.dao.AuthorDao;
import com.epam.mylibrary.action.Action;
import com.epam.mylibrary.constants.Const;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.epam.mylibrary.dao.exception.DaoException;
import com.epam.mylibrary.action.exception.ActionException;

import java.util.List;

public class ShowBookFormAction implements Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        String resultPage = null;
        if (req.getSession().getAttribute(Const.BOOK) != null) {
            List<Author> allAuthors;
            try {
                allAuthors = new AuthorDao().findAll();
            } catch (DaoException e) {
                throw new ActionException();
            }
            req.getSession().setAttribute(Const.ALL_AUTHORS, allAuthors);
            resultPage = Const.FORWARD_EDIT_BOOK_FORM;
        }
        return resultPage;
}
}
