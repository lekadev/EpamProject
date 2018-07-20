package com.mylibrary.service;

import java.sql.*;
import java.util.List;
import com.mylibrary.dao.*;
import com.mylibrary.model.*;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import com.mylibrary.db.ConnectionPool;
import com.mylibrary.dao.exception.DaoException;
import com.mylibrary.service.exception.ServiceException;

public class OrderService {

    private ConnectionPool pool = ConnectionPool.getInstance();
    private final static Logger logger = Logger.getLogger(OrderService.class);

    public OrderService() { }

    public List<Order> findAllOrders() throws ServiceException {
        List<Order> orders;
        Connection connection = null;
        try {
            connection = pool.takeConnection();
            connection.setAutoCommit(false);
            OrderDao orderDao = new OrderDao(connection);
            UserDao userDao = new UserDao(connection);
            BookDao bookDao = new BookDao(connection);
            AuthorDao authorDao = new AuthorDao(connection);
            orders = orderDao.findAll();
            for(Order order : orders) {
                User user = userDao.findById(order.getUser().getId());
                order.setUser(user);
                Book book = bookDao.findById(order.getBook().getId());
                if(book != null) {
                    List<Author> authors = authorDao.findAuthorsOfBook(book);
                    book.setAuthors(authors);
                    order.setBook(book);
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException | DaoException e1) {
            logger.log(Level.ERROR, "Unable to execute transaction of getting list of all orders", e1);
            try {
                if (connection != null) {
                    connection.rollback();
                    logger.log(Level.ERROR, "Transaction rollback");
                }
            } catch (SQLException e2) {
                logger.log(Level.ERROR, "Unable to rollback transaction", e2);
            }
            throw new ServiceException();
        } finally {
            pool.closeConnection(connection);
        }
        return orders;
    }

    public List<Order> findOrdersOfUser(User user) throws ServiceException {
        List<Order> orders;
        Connection connection = null;
        try {
            connection = pool.takeConnection();
            connection.setAutoCommit(false);
            OrderDao orderDao = new OrderDao(connection);
            BookDao bookDao = new BookDao(connection);
            AuthorDao authorDao = new AuthorDao(connection);
            orders = orderDao.findOrdersOfUser(user);
            for(Order order : orders) {
                order.setUser(user);
                Book book = bookDao.findById(order.getBook().getId());
                if(book != null) {
                    List<Author> authors = authorDao.findAuthorsOfBook(book);
                    book.setAuthors(authors);
                    order.setBook(book);
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException | DaoException e1) {
            logger.log(Level.ERROR, "Unable to execute transaction of finding orders of user", e1);
            try {
                if (connection != null) {
                    connection.rollback();
                    logger.log(Level.ERROR, "Transaction rollback");
                }
            } catch (SQLException e2) {
                logger.log(Level.ERROR, "Unable to rollback transaction", e2);
            }
            throw new ServiceException();
        } finally {
            pool.closeConnection(connection);
        }
        return orders;
    }
}
