package com.mylibrary.action.post;

import java.util.List;
import java.util.ArrayList;
import com.mylibrary.action.*;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import com.mylibrary.model.Book;
import com.mylibrary.model.Author;
import com.mylibrary.dao.BookDao;
import com.mylibrary.dao.AuthorDao;
import com.mylibrary.db.ConnectionPool;
import com.mylibrary.service.BookService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AddBookAction implements Action {

    private final static Logger logger = Logger.getLogger(AddBookAction.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String resultPage = Paths.SHOW_CATALOGUE;
        req.getSession().removeAttribute(Attributes.BOOK_INPUT_MESSAGE);
        String title = req.getParameter(Parameters.BOOK_TITLE);
        String publisher = req.getParameter(Parameters.BOOK_PUBLISHER);
        String numberParameter = req.getParameter(Parameters.BOOK_COPIES);
        String[] idAuthorParams = req.getParameterValues(Parameters.AUTHORS_SELECTED);
        boolean fieldsValid = InputValidator.validateText(title) && InputValidator.validateText(publisher) && InputValidator.validateInteger(numberParameter);
        if(!fieldsValid) {
            req.getSession().setAttribute(Attributes.BOOK_INPUT_MESSAGE, ErrorMessages.TEXT_INPUT_ERROR);
            return resultPage;
        }
        if(idAuthorParams == null) {
            req.getSession().setAttribute(Attributes.BOOK_INPUT_MESSAGE, ErrorMessages.NO_AUTHOR_ERROR);
            return resultPage;
        }
        List<Author> authors = new ArrayList<>();
        for(String stringId : idAuthorParams) {
            int id = Integer.parseInt(stringId);
            Author author = new Author();
            author.setId(id);
            authors.add(author);
        }
        Book newBook = new Book();
        newBook.setTitle(title);
        newBook.setPublisher(publisher);
        newBook.setNumberCopies(Integer.parseInt(numberParameter));
        newBook.setAuthors(authors);
        ConnectionPool pool = ConnectionPool.getInstance();
        int idBook = new BookService(pool).addBook(newBook);
        if(idBook != 0) {
            List<Book> catalogue = new BookDao(pool).findAll();
            for(Book book : catalogue) {
                book.setAuthors(new AuthorDao(pool).findAuthorsOfBook(book.getId()));
            }
            req.getServletContext().setAttribute(Attributes.CATALOGUE, catalogue);
            req.getSession().setAttribute(Attributes.BOOK_INPUT_MESSAGE, ErrorMessages.BOOK_SUCCESS_MESSAGE);
            logger.log(Level.INFO, "New book was added: " + newBook.toString());
        }
        return resultPage;
    }
}
