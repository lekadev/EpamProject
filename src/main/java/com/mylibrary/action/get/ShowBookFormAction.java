package com.mylibrary.action.get;

import com.mylibrary.action.*;
import com.mylibrary.model.Book;
import com.mylibrary.model.Author;
import com.mylibrary.dao.AuthorDao;
import com.mylibrary.service.BookService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mylibrary.validator.InputValidator;
import com.mylibrary.dao.exception.DaoException;
import com.mylibrary.action.exception.ActionException;
import com.mylibrary.service.exception.ServiceException;

import java.util.List;

public class ShowBookFormAction implements Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        String resultPage = null;
        List<Author> allAuthors;
        try {
            allAuthors = new AuthorDao().findAll();
        } catch (DaoException e) {
            throw new ActionException();
        }
        req.setAttribute(Attributes.ALL_AUTHORS, allAuthors);
        String idParameter = req.getParameter(Parameters.BOOK_ID);
        boolean idValid = InputValidator.isIntegerValid(idParameter);
        if(idValid) {
            int idBook = Integer.parseInt(idParameter);
            Book book;
            try {
                book = new BookService().findBookById(idBook);
            } catch (ServiceException e) {
                throw new ActionException();
            }
            req.setAttribute(Attributes.BOOK, book);
            resultPage = Paths.PAGE_EDIT_BOOK_FORM;
        } else if(req.getSession().getAttribute(Attributes.BOOK) != null) {
            resultPage = Paths.PAGE_EDIT_BOOK_FORM;
        }
        return resultPage;
    }
}
