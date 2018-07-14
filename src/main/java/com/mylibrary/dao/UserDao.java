package com.mylibrary.dao;

import java.sql.*;
import java.util.List;
import com.mylibrary.model.*;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import com.mylibrary.db.DBColumns;
import com.mylibrary.db.ConnectionPool;

public class UserDao extends EntityDao<Integer, User> {

    private ConnectionPool pool;
    private final static Logger logger = Logger.getLogger(UserDao.class);
    private final static String SELECT_BY_ID = "SELECT * FROM library.user_view WHERE id_user=?";
    private final static String SELECT_BY_EMAIL = "SELECT * FROM library.user_view WHERE email=?";
    private final static String SELECT_BY_EMAIL_AND_PASSWORD = "SELECT * FROM library.user_view WHERE email=? AND password=?";
    private final static String UPDATE_LIBRARIAN = "UPDATE library.librarian SET name_first=?, name_last=?, number_phone=? WHERE id_user=?";
    private final static String UPDATE_READER = "UPDATE library.reader SET name_first=?, name_last=? WHERE id_user=?";
    private final static String UPDATE_PASSWORD = "UPDATE library.user SET password=? WHERE id_user=?";


    public UserDao(ConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public List<User> findAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public User findById(Integer id) {
        User user = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.takeConnection();
            statement = connection.prepareStatement(SELECT_BY_ID);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = retrieveUser(resultSet);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to find user by id", e);
        } finally {
            pool.closeConnection(connection, statement, resultSet);
        }
        return user;
    }

    @Override
    public int deleteById(Integer id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer create(User user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int update(User user) {
        int rowsUpdated = 0;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = pool.takeConnection();
            switch (user.getRole()) {
                case LIBRARIAN:
                    statement = connection.prepareStatement(UPDATE_LIBRARIAN);
                    statement.setString(1, user.getNameFirst());
                    statement.setString(2, user.getNameLast());
                    statement.setString(3, ((Librarian)user).getNumberPhone());
                    statement.setInt(4, user.getId());
                    rowsUpdated = statement.executeUpdate();
                    break;
                case READER:
                    statement = connection.prepareStatement(UPDATE_READER);
                    statement.setString(1, user.getNameFirst());
                    statement.setString(2, user.getNameLast());
                    statement.setInt(3, user.getId());
                    rowsUpdated = statement.executeUpdate();
                    break;
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to update user", e);
        } finally {
            pool.closeConnection(connection, statement);
        }
        return rowsUpdated;
    }

    public boolean isRegistered(String email) {
        boolean isRegistered = true;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.takeConnection();
            statement = connection.prepareStatement(SELECT_BY_EMAIL);
            statement.setString(1, email);
            resultSet  =statement.executeQuery();
            if(!resultSet.next()) {
                isRegistered = false;
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to check email", e);
        } finally {
            pool.closeConnection(connection, statement, resultSet);
        }
        return isRegistered;
    }

    public User findByEmailAndPassword(String email, String password) {
        User user = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            connection = pool.takeConnection();
            statement = connection.prepareStatement(SELECT_BY_EMAIL_AND_PASSWORD);
            statement.setString(1, email);
            statement.setString(2, password);
            resultSet = statement.executeQuery();
            if(resultSet.next()) {
                user = retrieveUser(resultSet);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to find user by email and password", e);
        } finally {
            pool.closeConnection(connection, statement, resultSet);
        }
        return user;
    }

    public int changePassword(User user) {
        int rowsUpdated = 0;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = pool.takeConnection();
            statement = connection.prepareStatement(UPDATE_PASSWORD);
            statement.setString(1, user.getPassword());
            statement.setInt(2, user.getId());
            rowsUpdated = statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to update password", e);
        } finally {
            pool.closeConnection(connection, statement);
        }
        return rowsUpdated;
    }

    private User retrieveUser(ResultSet resultSet) {
        User user = new User();
        try {
            User.Role role = User.Role.valueOf(resultSet.getString(DBColumns.USER_ROLE).toUpperCase());
            switch (role) {
                case LIBRARIAN: user = new Librarian();
                    ((Librarian) user).setNumberPhone((resultSet.getString(DBColumns.LIBRARIAN_NUMBER_PHONE)));
                    break;
                case READER: user = new Reader();
                    ((Reader) user).setDateRegistered(resultSet.getDate(DBColumns.READER_DATE_REGISTERED));
                    break;
            }
            user.setId(resultSet.getInt(DBColumns.USER_ID));
            user.setEmail(resultSet.getString(DBColumns.USER_EMAIL));
            user.setPassword(resultSet.getString(DBColumns.USER_PASSWORD));
            user.setNameFirst(resultSet.getString(DBColumns.USER_NAME_FIRST));
            user.setNameLast(resultSet.getString(DBColumns.USER_NAME_LAST));
            user.setRole(role);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to parse result set for user", e);
        }
        return user;
    }
}
