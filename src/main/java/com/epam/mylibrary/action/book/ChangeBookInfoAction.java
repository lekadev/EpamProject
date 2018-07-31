package com.epam.mylibrary.action.book;

import java.util.List;
import java.util.ArrayList;
import com.epam.mylibrary.entity.Book;
import com.epam.mylibrary.entity.Author;
import com.epam.mylibrary.dao.AuthorDao;
import com.epam.mylibrary.action.Action;
import com.epam.mylibrary.constants.Const;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.epam.mylibrary.service.BookService;
import com.epam.mylibrary.dao.exception.DaoException;
import com.epam.mylibrary.action.exception.ActionException;
import com.epam.mylibrary.service.exception.ServiceException;
import static com.epam.mylibrary.validator.FormValidator.isBookFormValid;

public class ChangeBookInfoAction implements Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        if (!isBookFormValid(req)) {
            return Const.REDIRECT_BOOK_EDIT_FORM;
        }
        Book book = setBook(req);
        String[] idAuthors = req.getParameterValues(Const.PARAM_AUTHORS);
        List<Author> authors = new ArrayList<>();
        for (String idAuthor : idAuthors) {
            Author author;
            try {
                author = new AuthorDao().findById(Integer.parseInt(idAuthor));
            } catch (DaoException e) {
                throw new ActionException();
            }
            authors.add(author);
        }
        book.setAuthors(authors);
        BookService bookService = new BookService();
        try {
            bookService.updateBook(book);
        } catch (ServiceException e) {
            throw new ActionException();
        }
        req.getSession().setAttribute(Const.BOOK_FORM_MESSAGE, Const.UPDATE_SUCCESS);
        req.getSession().setAttribute(Const.BOOK, book);
        return Const.REDIRECT_BOOK_EDIT_FORM;
    }

    private Book setBook(HttpServletRequest req) {
        String title = req.getParameter(Const.PARAM_TITLE);
        String publisher = req.getParameter(Const.PARAM_PUBLISHER);
        String numberCopies = req.getParameter(Const.PARAM_COPIES);
        Book book = (Book) req.getSession().getAttribute(Const.BOOK);
        book.setTitle(title);
        book.setPublisher(publisher);
        book.setNumberCopies(Integer.parseInt(numberCopies));
        return book;
    }
}
