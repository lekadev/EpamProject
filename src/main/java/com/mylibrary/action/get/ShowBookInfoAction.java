package com.mylibrary.action.get;

import com.mylibrary.action.*;
import com.mylibrary.entity.Book;
import com.mylibrary.service.BookService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mylibrary.validator.InputValidator;
import com.mylibrary.action.exception.ActionException;
import com.mylibrary.service.exception.ServiceException;

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