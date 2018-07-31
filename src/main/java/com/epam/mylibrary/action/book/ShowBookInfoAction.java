package com.epam.mylibrary.action.book;

import com.epam.mylibrary.entity.Book;
import com.epam.mylibrary.action.Action;
import com.epam.mylibrary.constants.Const;
import com.epam.mylibrary.service.BookService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.epam.mylibrary.validator.FieldValidator;
import com.epam.mylibrary.action.exception.ActionException;
import com.epam.mylibrary.service.exception.ServiceException;

public class ShowBookInfoAction implements Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        String resultPage = null;
        String idParameter = req.getParameter(Const.PARAM_BOOK_ID);
        boolean idValid = FieldValidator.isIntegerValid(idParameter);
        if (idValid) {
            int idBook = Integer.parseInt(idParameter);
            Book book;
            try {
                book = new BookService().findBookById(idBook);
            } catch (ServiceException e) {
                throw new ActionException();
            }
            req.getSession().setAttribute(Const.BOOK, book);
            resultPage = Const.FORWARD_BOOK_INFO;
        } else if(req.getSession().getAttribute(Const.BOOK) != null) {
            resultPage = Const.FORWARD_BOOK_INFO;
        }
        return resultPage;
    }
}