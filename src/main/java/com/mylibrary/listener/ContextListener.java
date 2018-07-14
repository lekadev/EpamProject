package com.mylibrary.listener;

import java.util.List;
import javax.servlet.*;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import com.mylibrary.model.Book;
import com.mylibrary.model.Order;
import com.mylibrary.model.Author;
import com.mylibrary.dao.BookDao;
import com.mylibrary.dao.AuthorDao;
import com.mylibrary.db.ConnectionPool;
import com.mylibrary.action.Attributes;

public class ContextListener implements ServletContextListener {

    private final static Logger logger = Logger.getLogger(ContextListener.class);

    private ConnectionPool pool;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        logger.log(Level.INFO, "The app was initialized");

        pool = ConnectionPool.getInstance();

        List<Book> catalogue = new BookDao(pool).findAll();
        for(Book book : catalogue) {
            book.setAuthors(new AuthorDao(pool).findAuthorsOfBook(book.getId()));
        }
        ServletContext context = event.getServletContext();
        context.setAttribute(Attributes.CATALOGUE, catalogue);

        List<Author> allAuthors = new AuthorDao(pool).findAll();
        context.setAttribute(Attributes.ALL_AUTHORS, allAuthors);

        Order.OrderStatus[] allStatuses = Order.OrderStatus.values();
        context.setAttribute(Attributes.ALL_STATUSES, allStatuses);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        pool.dispose();
    }
}
