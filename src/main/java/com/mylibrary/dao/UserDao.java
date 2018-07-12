package com.mylibrary.dao;

import java.sql.*;
import java.util.Calendar;
import com.mylibrary.model.*;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import com.mylibrary.db.DBColumns;
import com.mylibrary.db.ConnectionPool;

public class UserDao {

    private ConnectionPool pool;
    private final static Logger logger = Logger.getLogger(UserDao.class);

    private final String SELECT_ALL = "SELECT * FROM library.user_view";
    private final String SELECT_BY_ID = "SELECT * FROM library.user_view WHERE id_user=?";
    private final String SELECT_BY_EMAIL = "SELECT * FROM library.user_view WHERE email=?";
    private final String SELECT_BY_EMAIL_AND_PASSWORD = "SELECT * FROM library.user_view WHERE email=? AND password=?";
    private final String UPDATE_LIBRARIAN = "UPDATE library.librarian SET name_first=?, name_last=?, number_phone=? WHERE id_user=?";
    private final String UPDATE_READER = "UPDATE library.reader SET name_first=?, name_last=? WHERE id_user=?";
    private final String UPDATE_PASSWORD = "UPDATE library.user SET password=? WHERE id_user=?";


    public UserDao(ConnectionPool pool) {
        this.pool = pool;
    }

    public boolean isRegistered(String email) {
        boolean isRegistered = true;
        String sql = SELECT_BY_EMAIL;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.takeConnection();
            statement = connection.prepareStatement(sql);
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

    public User findById(int id) {
        User user = null;
        String sql = SELECT_BY_ID;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.takeConnection();
            statement = connection.prepareStatement(sql);
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

    public User findByEmailAndPassword(String email, String password) {
        User user = null;
        String sql = SELECT_BY_EMAIL_AND_PASSWORD;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            connection = pool.takeConnection();
            statement = connection.prepareStatement(sql);
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

    public int update(User user) {
        int rowsUpdated = 0;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = pool.takeConnection();
            switch (user.getRole()) {
                case LIBRARIAN:
                    statement = connection.prepareStatement(UPDATE_LIBRARIAN);
                    statement.setString(3, ((Librarian)user).getNumberPhone());
                    statement.setInt(4, user.getId());
                    break;
                case READER:
                    statement = connection.prepareStatement(UPDATE_READER);
                    statement.setInt(3, user.getId());
                    break;
            }
            statement.setString(1, user.getNameFirst());
            statement.setString(2, user.getNameLast());
            rowsUpdated = statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to update user", e);
        } finally {
            pool.closeConnection(connection, statement);
        }
        return rowsUpdated;
    }

    public int changePassword(User user) {
        String sql = UPDATE_PASSWORD;
        int rowsUpdated = 0;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = pool.takeConnection();
            statement = connection.prepareStatement(sql);
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

    public int createReader(Reader reader){
        String userSql = "INSERT INTO library.user (email, password, role) VALUES (?, ?, ?)";
        String readerSql = "INSERT INTO library.reader(name_first, name_last, date_registered, id_user) VALUES (?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement userStatement = null;
        PreparedStatement readerStatement = null;
        ResultSet generatedKey = null;
        int idUser = 0;
        try {
            connection = pool.takeConnection();
            connection.setAutoCommit(false);
            userStatement = connection.prepareStatement(userSql, PreparedStatement.RETURN_GENERATED_KEYS);
            userStatement.setString(1, reader.getEmail());
            userStatement.setString(2, reader.getPassword());
            userStatement.setString(3, reader.getRole().toString());
            userStatement.executeUpdate();
            generatedKey = userStatement.getGeneratedKeys();
            int generatedId = 0;
            if(generatedKey.next()) {
                generatedId = generatedKey.getInt(1);
                if(generatedId != 0) {
                    readerStatement = connection.prepareStatement(readerSql);
                    readerStatement.setString(1, reader.getNameFirst());
                    readerStatement.setString(2, reader.getNameLast());
                    java.sql.Date currentDate = new Date(Calendar.getInstance().getTimeInMillis());
                    readerStatement.setDate(3, currentDate);
                    readerStatement.setInt(4, generatedId);
                    readerStatement.executeUpdate();
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
            idUser = generatedId;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to execute new user insertion transaction", e);
            try {
                connection.rollback();
            } catch (SQLException e1) {
                logger.log(Level.ERROR, "Unable to roll back user insertion transaction", e);
            }
        } finally {
            pool.closeConnection(connection);
        }
        return idUser;
    }

    private User retrieveUser(ResultSet resultSet) {
        User user = null;
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
