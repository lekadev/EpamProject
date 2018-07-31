package com.epam.mylibrary.action.book;

import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import com.epam.mylibrary.entity.Book;
import com.epam.mylibrary.entity.Author;
import com.epam.mylibrary.action.Action;
import com.epam.mylibrary.constants.Const;
import com.epam.mylibrary.service.BookService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.epam.mylibrary.action.exception.ActionException;
import com.epam.mylibrary.service.exception.ServiceException;
import static com.epam.mylibrary.validator.FormValidator.isBookFormValid;

public class AddBookAction implements Action {

    private final static Logger logger = Logger.getLogger(AddBookAction.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        if (!isBookFormValid(req)) {
            return Const.REDIRECT_CATALOGUE;
        }
        String title = req.getParameter(Const.PARAM_TITLE);
        String publisher = req.getParameter(Const.PARAM_PUBLISHER);
        String numberCopiesString = req.getParameter(Const.PARAM_COPIES);
        String[] idAuthors = req.getParameterValues(Const.PARAM_AUTHORS);
        List<Author> authors = new ArrayList<>();
        for (String idAuthor : idAuthors) {
            Author author = new Author();
            author.setId(Integer.parseInt(idAuthor));
            authors.add(author);
        }
        Book book = new Book();
        book.setAuthors(authors);
        book.setTitle(title);
        book.setPublisher(publisher);
        book.setNumberCopies(Integer.parseInt(numberCopiesString));
        BookService bookService = new BookService();
        try {
            bookService.createBook(book);
        } catch (ServiceException e) {
            throw new ActionException();
        }
        req.getSession().setAttribute(Const.BOOK_FORM_MESSAGE, Const.BOOK_ADD_SUCCESS);
        logger.log(Level.INFO, "New book was added: ID#" + book.getId() + " " + book.getTitle());
        return Const.REDIRECT_CATALOGUE;
    }
}
