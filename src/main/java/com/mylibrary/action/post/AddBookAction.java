package com.mylibrary.action.post;

import java.util.List;
import java.util.ArrayList;
import com.mylibrary.action.*;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import com.mylibrary.model.Book;
import com.mylibrary.model.Author;
import com.mylibrary.db.ConnectionPool;
import com.mylibrary.service.BookService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mylibrary.validator.InputValidator;
import com.mylibrary.action.exception.ActionException;
import com.mylibrary.service.exception.ServiceException;

public class AddBookAction implements Action {

    private final static Logger logger = Logger.getLogger(AddBookAction.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        String title = req.getParameter(Parameters.BOOK_TITLE);
        String publisher = req.getParameter(Parameters.BOOK_PUBLISHER);
        String numberCopiesString = req.getParameter(Parameters.BOOK_COPIES);
        String[] idAuthorsSelected = req.getParameterValues(Parameters.AUTHORS_SELECTED);
        boolean fieldsValid = InputValidator.isTextValid(title) && InputValidator.isTextValid(publisher) && InputValidator.isIntegerValid(numberCopiesString);
        if(!fieldsValid) {
            req.getSession().setAttribute(Attributes.BOOK_ADD_MESSAGE, ErrorMessages.TEXT_INPUT_ERROR);
            return Paths.REDIRECT_CATALOGUE;
        }
        if(idAuthorsSelected == null) {
            req.getSession().setAttribute(Attributes.BOOK_ADD_MESSAGE, ErrorMessages.NO_AUTHOR_ERROR);
            return Paths.REDIRECT_CATALOGUE;
        }
        List<Author> authors = new ArrayList<>();
        for(String stringId : idAuthorsSelected) {
            Author author = new Author();
            author.setId(Integer.parseInt(stringId));
            authors.add(author);
        }
        Book newBook = new Book();
        newBook.setAuthors(authors);
        newBook.setTitle(title);
        newBook.setPublisher(publisher);
        newBook.setNumberCopies(Integer.parseInt(numberCopiesString));
        ConnectionPool pool = ConnectionPool.getInstance();
        BookService bookService = new BookService(pool);
        try {
            bookService.createBook(newBook);
        } catch (ServiceException e) {
            throw new ActionException();
        }
        req.getSession().setAttribute(Attributes.BOOK_ADD_MESSAGE, ErrorMessages.BOOK_ADD_SUCCESS);
        logger.log(Level.INFO, "New book was added: ID#" + newBook.getId() + " " + newBook.getTitle());
        return Paths.REDIRECT_CATALOGUE;
    }
}
