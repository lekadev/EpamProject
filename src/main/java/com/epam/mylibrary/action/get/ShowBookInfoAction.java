package com.epam.mylibrary.action.get;

import com.epam.mylibrary.action.Action;
import com.epam.mylibrary.action.Attributes;
import com.epam.mylibrary.action.Parameters;
import com.epam.mylibrary.action.Paths;
import com.epam.mylibrary.service.BookService;
import com.epam.mylibrary.action.*;
import com.epam.mylibrary.entity.Book;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.epam.mylibrary.validator.InputValidator;
import com.epam.mylibrary.action.exception.ActionException;
import com.epam.mylibrary.service.exception.ServiceException;

public class ShowBookInfoAction implements Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        String resultPage = null;
        String idParameter = req.getParameter(Parameters.BOOK_ID);
        boolean idValid = InputValidator.isIntegerValid(idParameter);
        if (idValid) {
            int idBook = Integer.parseInt(idParameter);
            Book book;
            try {
                book = new BookService().findBookById(idBook);
            } catch (ServiceException e) {
                throw new ActionException();
            }
            req.setAttribute(Attributes.BOOK, book);
            resultPage = Paths.PAGE_BOOK_INFO;
        }
        return resultPage;
    }
}