package com.epam.mylibrary.action.get;

import java.util.List;

import com.epam.mylibrary.action.Action;
import com.epam.mylibrary.action.Attributes;
import com.epam.mylibrary.action.Paths;
import com.epam.mylibrary.service.BookService;
import com.epam.mylibrary.action.*;
import com.epam.mylibrary.entity.Book;
import com.epam.mylibrary.entity.Author;
import com.epam.mylibrary.dao.AuthorDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        req.setAttribute(Attributes.CATALOGUE, catalogue);
        req.setAttribute(Attributes.ALL_AUTHORS, allAuthors);
        return Paths.PAGE_CATALOGUE;
    }
}
