package com.epam.mylibrary.dao;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

import com.epam.mylibrary.entity.Book;
import com.epam.mylibrary.entity.Order;
import com.epam.mylibrary.entity.User;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import com.epam.mylibrary.db.DBColumns;
import com.epam.mylibrary.db.ConnectionPool;
import java.net.UnknownServiceException;
import com.epam.mylibrary.dao.exception.DaoException;

public class OrderDao extends EntityDao<Integer, Order> {

    private final static Logger logger = Logger.getLogger(OrderDao.class);

    private final static String SELECT_ALL = "SELECT * FROM library.order ORDER BY date DESC";
    private final static String SELECT_BY_USER_ID = "SELECT * FROM library.order WHERE id_user=? ORDER BY date DESC";
    private final static String UPDATE_STATUS_BY_ORDER_ID = "UPDATE library.order SET status=? WHERE id_order=?";
    private final static String DELETE_BY_ORDER_ID = "DELETE FROM library.order WHERE id_order=?";
    private final static String INSERT = "INSERT INTO library.order (id_book, id_user, status) VALUES(?, ?, ?)";

    private ConnectionPool pool = ConnectionPool.getInstance();
    private Connection connection;

    public OrderDao() { }

    public OrderDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Order> findAll() throws DaoException {
        List<Order> orders = new ArrayList<>();
        PreparedStatement statement;
        ResultSet resultSet;
        try {
            statement = connection.prepareStatement(SELECT_ALL);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = retrieveOrder(resultSet);
                orders.add(order);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to get list of all orders", e);
            throw new DaoException();
        }
        return orders;
    }

    @Override
    public Order findById(Integer id) throws DaoException {
        throw new DaoException(new UnsupportedOperationException());
    }

    @Override
    public void deleteById(Integer idOrder) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = pool.takeConnection();
            statement = connection.prepareStatement(DELETE_BY_ORDER_ID);
            statement.setInt(1, idOrder);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to delete order" ,e);
            throw new DaoException();
        } finally {
            pool.closeConnection(connection, statement);
        }
    }

    @Override
    public Integer create(Order order) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet generatedKey = null;
        int idOrder = 0;
        try {
            connection = pool.takeConnection();
            statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, order.getBook().getId());
            statement.setInt(2, order.getUser().getId());
            statement.setString(3, String.valueOf(order.getStatus()));
            statement.executeUpdate();
            generatedKey = statement.getGeneratedKeys();
            if (generatedKey.next()) {
                idOrder = generatedKey.getInt(1);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to insert order", e);
            throw new DaoException();
        } finally {
            pool.closeConnection(connection, statement, generatedKey);
        }
        return idOrder;
    }

    @Override
    public void update(Order entity) throws DaoException {
        throw new DaoException(new UnknownServiceException());
    }

    public List<Order> findOrdersOfUser(User user) throws DaoException {
        List<Order> orders = new ArrayList<>();
        PreparedStatement statement;
        ResultSet resultSet;
        try {
            statement = connection.prepareStatement(SELECT_BY_USER_ID);
            statement.setInt(1, user.getId());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = retrieveOrder(resultSet);
                order.setUser(user);
                orders.add(order);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to get user's list of orders", e);
            throw new DaoException();
        }
        return orders;
    }

    public void changeStatus(int idOrder, Order.OrderStatus status) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = pool.takeConnection();
            statement = connection.prepareStatement(UPDATE_STATUS_BY_ORDER_ID);
            statement.setString(1, String.valueOf(status));
            statement.setInt(2, idOrder);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to update status", e);
            throw new DaoException();
        } finally {
            pool.closeConnection(connection, statement);
        }
    }

    private Order retrieveOrder(ResultSet resultSet) throws DaoException {
        Order order = new Order();
        User user = new User();
        Book book = new Book();
        try {
            order.setId(resultSet.getInt(DBColumns.ORDER_ID));
            order.setDate(resultSet.getDate(DBColumns.ORDER_DATE));
            order.setStatus(Order.OrderStatus.valueOf(resultSet.getString(DBColumns.ORDER_STATUS)));
            book.setId(resultSet.getInt(DBColumns.BOOK_ID));
            user.setId(resultSet.getInt(DBColumns.USER_ID));
            order.setBook(book);
            order.setUser(user);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to parse result set for order", e);
            throw new DaoException();
        }
        return order;
    }
}