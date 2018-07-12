package com.mylibrary.dao;

import java.sql.*;
import java.util.List;
import java.util.Locale;
import java.util.ArrayList;
import com.mylibrary.model.*;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import com.mylibrary.db.DBColumns;
import com.mylibrary.db.ConnectionPool;

public class OrderDao {

    private ConnectionPool pool;
    private final static Logger logger = Logger.getLogger(OrderDao.class);

    private final static String SELECT_ALL = "SELECT * FROM library.order JOIN order_status USING(id_status) JOIN locale USING(id_locale) WHERE name_locale=? ORDER BY date DESC";
    private final static String SELECT_BY_USERID = "SELECT * FROM library.order JOIN order_status USING(id_status) JOIN locale USING(id_locale) WHERE id_user=? AND name_locale=? ORDER BY date DESC";
    private final static String UPDATE_STATUS_BY_ORDRID = "UPDATE library.order SET id_status=? WHERE id_order=?";
    private final static String DELETE_BY_ORDERID = "DELETE FROM library.order WHERE id_order=?";
    private final static String INSERT = "INSERT INTO library.order (id_book, id_user, id_status) VALUES(?, ?, ?)";


    public OrderDao(ConnectionPool pool) {
        this.pool = pool;
    }

    public List<Order> findOrdersOfUser(User user, Locale locale) {
        String sql = SELECT_BY_USERID;
        List<Order> orders = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.takeConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, user.getId());
            statement.setString(2, locale.getLanguage());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = retrieveOrder(resultSet);
                order.setUser(user);
                orders.add(order);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to get user's list of orders", e);
        } finally {
            pool.closeConnection(connection, statement, resultSet);
        }
        return orders;
    }

    public int deleteByOrderId(int idOrder) {
        String sql = DELETE_BY_ORDERID;
        Connection connection = null;
        PreparedStatement statement = null;
        int rowsUpdated = 0;
        try {
            connection = pool.takeConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, idOrder);
            rowsUpdated = statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to delete order" ,e);
        } finally {
            pool.closeConnection(connection, statement);
        }
        return rowsUpdated;
    }

    public int createOrder(Order order) {
        String sql = INSERT;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet generatedKey = null;
        int idOrder = 0;
        try {
            connection = pool.takeConnection();
            statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, order.getBook().getId());
            statement.setInt(2, order.getUser().getId());
            statement.setInt(3, order.getStatus().getId());
            statement.executeUpdate();
            generatedKey = statement.getGeneratedKeys();
            if(generatedKey.next()) {
                idOrder = generatedKey.getInt(1);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to insert order", e);
        } finally {
            pool.closeConnection(connection, statement, generatedKey);
        }
        return idOrder;
    }

    public int changeStatus(int idOrder, Order.OrderStatus status) {
        String sql = UPDATE_STATUS_BY_ORDRID;
        Connection connection = null;
        PreparedStatement statement = null;
        int rowsUpdated = 0;
        try {
            connection = pool.takeConnection();
            statement = connection.prepareStatement(UPDATE_STATUS_BY_ORDRID);
            statement.setInt(1, status.getId());
            statement.setInt(2, idOrder);
            rowsUpdated = statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to update status", e);
        } finally {
            pool.closeConnection(connection, statement);
        }
        return rowsUpdated;
    }

    public List<Order> findAllOrders(Locale locale) {
        List<Order> orders = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.takeConnection();
            statement = connection.prepareStatement(SELECT_ALL);
            statement.setString(1, locale.getLanguage());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = retrieveOrder(resultSet);
                orders.add(order);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to get list of all orders", e);
        } finally {
            pool.closeConnection(connection, statement, resultSet);
        }
        return orders;
    }

    private Order retrieveOrder(ResultSet resultSet) {
        Order order = new Order();
        User user = new User();
        Book book = new Book();
        Order.OrderStatus status;
        try {
            order.setId(resultSet.getInt(DBColumns.ORDER_ID));
            order.setDate(resultSet.getDate(DBColumns.ORDER_DATE));
            status = Order.OrderStatus.valueOf(resultSet.getString(DBColumns.ORDER_STATUS).toUpperCase());
            status.setDescription(resultSet.getString(DBColumns.STATUS_DESCRIPTION));
            order.setStatus(status);
            book.setId(resultSet.getInt(DBColumns.BOOK_ID));
            user.setId(resultSet.getInt(DBColumns.USER_ID));
            order.setBook(book);
            order.setUser(user);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to parse result set for order", e);
        }
        return order;
    }
}