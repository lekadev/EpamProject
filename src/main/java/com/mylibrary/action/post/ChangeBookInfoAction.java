package com.mylibrary.action.post;

import java.util.List;
import java.util.ArrayList;
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

public class ChangeBookInfoAction implements Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        String idBookString = req.getParameter(Parameters.BOOK_ID);
        if(idBookString == null) {
            throw new ActionException();
        }
        int idBook = Integer.parseInt(idBookString);
        BookService bookService = new BookService();
        Book bookOld;
        try {
            bookOld = bookService.findBookById(idBook);
        } catch (ServiceException e) {
            throw new ActionException();
        }
        if(bookOld == null) {
            throw new ActionException();
        }
        req.getSession().setAttribute(Attributes.BOOK, bookOld);
        String titleNew = req.getParameter(Parameters.BOOK_TITLE);
        String publisherNew = req.getParameter(Parameters.BOOK_PUBLISHER);
        String numberCopiesNew = req.getParameter(Parameters.BOOK_COPIES);
        boolean fieldsValid = InputValidator.isTextValid(titleNew) && InputValidator.isTextValid(publisherNew) && InputValidator.isIntegerValid(numberCopiesNew);
        if(!fieldsValid) {
            req.getSession().setAttribute(Attributes.BOOK_UPDATE_MESSAGE, ErrorMessages.TEXT_INPUT_ERROR);
            return Paths.REDIRECT_BOOK_EDIT_FORM;
        }
        String[] idAuthorsSelected = req.getParameterValues(Parameters.AUTHORS_SELECTED);
        if(idAuthorsSelected == null) {
            req.getSession().setAttribute(Attributes.BOOK_UPDATE_MESSAGE, ErrorMessages.NO_AUTHOR_ERROR);
            return Paths.REDIRECT_BOOK_EDIT_FORM;
        }
        List<Author> authorsNew = new ArrayList<>();
        for(String idAuthor : idAuthorsSelected) {
            Author author;
            try {
                author = new AuthorDao().findById(Integer.parseInt(idAuthor));
            } catch (DaoException e) {
                throw new ActionException();
            }
            authorsNew.add(author);
        }
        Book bookNew = new Book();
        bookNew.setId(idBook);
        bookNew.setTitle(titleNew);
        bookNew.setPublisher(publisherNew);
        bookNew.setNumberCopies(Integer.parseInt(numberCopiesNew));
        bookNew.setAuthors(authorsNew);
        if(bookNew.equals(bookOld)) {
            return Paths.REDIRECT_BOOK_EDIT_FORM;
        }
        try {
            bookService.updateBook(bookNew);
        } catch (ServiceException e) {
            throw new ActionException();
        }
        req.getSession().setAttribute(Attributes.BOOK_UPDATE_MESSAGE, ErrorMessages.UPDATE_SUCCESS);
        req.getSession().setAttribute(Attributes.BOOK, bookNew);
        return Paths.REDIRECT_BOOK_EDIT_FORM;
    }
}
