package com.epam.mylibrary.action.book;

import java.util.List;
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

public class ShowCatalogueAction implements Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        List<Book> catalogue;
        List<Author> allAuthors;
        try {
            catalogue = new BookService().findAllBooks();
            allAuthors = new AuthorDao().findAll();
        } catch (ServiceException |DaoException e) {
            throw new ActionException();
        }
        req.setAttribute(Const.CATALOGUE, catalogue);
        req.setAttribute(Const.ALL_AUTHORS, allAuthors);
        return Const.FORWARD_CATALOGUE;
    }
}
