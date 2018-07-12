package com.mylibrary.action.post;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.mylibrary.action.*;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import com.mylibrary.model.Book;
import com.mylibrary.dao.BookDao;
import com.mylibrary.db.ConnectionPool;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AddBookAction implements Action {

    private final static Logger logger = Logger.getLogger(AddBookAction.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String resultPage = Paths.SHOW_CATALOGUE;
        req.getSession().removeAttribute(Attributes.BOOK_INPUT_MESSAGE);
        String title = req.getParameter("title");
        String publisher = req.getParameter("publisher");
        String numberParameter = req.getParameter("numberCopies");
        String[] idAuthorParams = req.getParameterValues("selectedAuthors");
        boolean fieldsValid = InputValidator.validateInputField(title) && InputValidator.validateInputField(publisher) && InputValidator.validateNumber(numberParameter);
        if(!fieldsValid) {
            req.getSession().setAttribute(Attributes.BOOK_INPUT_MESSAGE, ErrorMessages.TEXT_INPUT_ERROR);
            return resultPage;
        }
        if(idAuthorParams == null) {
            req.getSession().setAttribute(Attributes.BOOK_INPUT_MESSAGE, ErrorMessages.NO_AUTHOR_ERROR);
            return resultPage;
        }
        List<Book.Author> authors = new ArrayList<>();
        for(String stringId : idAuthorParams) {
            int id = Integer.parseInt(stringId);
            Book.Author author = new Book.Author();
            author.setId(id);
            authors.add(author);
        }
        Book book = new Book();
        book.setTitle(title);
        book.setPublisher(publisher);
        book.setNumberCopies(Integer.parseInt(numberParameter));
        book.setAuthors(authors);
        ConnectionPool pool = ConnectionPool.getInstance();
        int id = new BookDao(pool).create(book);
        if(id != 0) {
            Map<Integer, Book> catalogue = new BookDao(pool).findAllBooks();
            req.getServletContext().setAttribute(Attributes.CATALOGUE, catalogue);
            logger.log(Level.INFO, "New book was added: " + book.toString());
        }
        return resultPage;
    }
}
