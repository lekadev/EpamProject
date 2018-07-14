package com.mylibrary.action.get;

import com.mylibrary.action.*;
import com.mylibrary.dao.AuthorDao;
import com.mylibrary.model.Author;
import com.mylibrary.model.Book;
import com.mylibrary.dao.BookDao;
import com.mylibrary.db.ConnectionPool;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowBookAction implements Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String resultPage = null;
        String idParameter = req.getParameter(Parameters.BOOK_ID);
        boolean idValid = InputValidator.validateInteger(idParameter);
        if(idValid) {
            int idBook = Integer.parseInt(idParameter);
            ConnectionPool pool = ConnectionPool.getInstance();
            Book book = new BookDao(pool).findById(idBook);
            List<Author> authors = new AuthorDao(pool).findAuthorsOfBook(idBook);
            req.setAttribute(Attributes.BOOK, book);
            resultPage = Paths.BOOK_INFO_PAGE;
        }
        return resultPage;
    }
}