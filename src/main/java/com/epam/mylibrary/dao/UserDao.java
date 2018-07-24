package com.epam.mylibrary.dao;

import java.sql.*;
import java.util.List;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import com.epam.mylibrary.entity.*;
import com.epam.mylibrary.db.DBColumns;
import com.epam.mylibrary.db.ConnectionPool;
import com.epam.mylibrary.dao.exception.DaoException;

public class UserDao extends EntityDao<Integer, User> {

    private final static Logger logger = Logger.getLogger(UserDao.class);

    private final static String SELECT_BY_ID = "SELECT * FROM library.user_view WHERE id_user=?";
    private final static String SELECT_BY_EMAIL = "SELECT * FROM library.user_view WHERE email=?";
    private final static String SELECT_BY_EMAIL_AND_PASSWORD = "SELECT * FROM library.user_view WHERE email=? AND password=?";
    private final static String UPDATE_LIBRARIAN = "UPDATE library.librarian SET name_first=?, name_last=?, number_phone=? WHERE id_user=?";
    private final static String UPDATE_READER = "UPDATE library.reader SET name_first=?, name_last=? WHERE id_user=?";
    private final static String UPDATE_PASSWORD = "UPDATE library.user SET password=? WHERE id_user=?";
    private final static String INSERT_USER = "INSERT INTO library.user (email, password, role) VALUES (?, ?, ?)";
    private final static String INSERT_READER = "INSERT INTO library.reader(name_first, name_last, date_registered, id_user) VALUES (?, ?, ?, ?)";

    private Connection connection;
    private ConnectionPool pool = ConnectionPool.getInstance();

    public UserDao() { }

    public UserDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<User> findAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public User findById(Integer id) throws DaoException {
        User user = null;
        PreparedStatement statement;
        ResultSet resultSet;
        try {
            statement = connection.prepareStatement(SELECT_BY_ID);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = retrieveUser(resultSet);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to find user by id", e);
            throw new DaoException();
        }
        return user;
    }

    @Override
    public void deleteById(Integer id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer create(User user) throws DaoException {
        PreparedStatement statement;
        ResultSet generatedKey;
        int idUser = 0;
        try {
            statement = connection.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, String.valueOf(user.getRole()));
            statement.executeUpdate();
            generatedKey = statement.getGeneratedKeys();
            if (generatedKey.next()) {
                idUser = generatedKey.getInt(1);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to execute new user insertion", e);
            throw new DaoException();
        }
        return idUser;
    }

    @Override
    public void update(User user) throws DaoException {
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
                    statement.executeUpdate();
                    break;
                case READER:
                    statement = connection.prepareStatement(UPDATE_READER);
                    statement.setString(1, user.getNameFirst());
                    statement.setString(2, user.getNameLast());
                    statement.setInt(3, user.getId());
                    statement.executeUpdate();
                    break;
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to update user", e);
            throw new DaoException();
        } finally {
            pool.closeConnection(connection, statement);
        }
    }

    public boolean isRegistered(String email) throws DaoException {
        boolean isRegistered = true;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.takeConnection();
            statement = connection.prepareStatement(SELECT_BY_EMAIL);
            statement.setString(1, email);
            resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                isRegistered = false;
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to check user by email", e);
            throw new DaoException();
        } finally {
            pool.closeConnection(connection, statement, resultSet);
        }
        return isRegistered;
    }

    public User findByEmailAndPassword(String email, String password) throws DaoException {
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
            if (resultSet.next()) {
                user = retrieveUser(resultSet);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to find user by email and password", e);
            throw new DaoException();
        } finally {
            pool.closeConnection(connection, statement, resultSet);
        }
        return user;
    }

    public void changePassword(User user) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = pool.takeConnection();
            statement = connection.prepareStatement(UPDATE_PASSWORD);
            statement.setString(1, user.getPassword());
            statement.setInt(2, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to update password", e);
            throw new DaoException();
        } finally {
            pool.closeConnection(connection, statement);
        }
    }

    public void createReader(Reader reader) throws DaoException {
        PreparedStatement statement;
        ResultSet generatedKey;
        try {
            statement = connection.prepareStatement(INSERT_READER, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, reader.getNameFirst());
            statement.setString(2, reader.getNameLast());
            statement.setDate(3, (Date) reader.getDateRegistered());
            statement.setInt(4, reader.getId());
            statement.executeUpdate();
            generatedKey = statement.getGeneratedKeys();
            if(generatedKey.next()) {
                generatedKey.getInt(1);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to execute new reader insertion", e);
            throw new DaoException();
        }
    }

    private User retrieveUser(ResultSet resultSet) throws DaoException {
        User user = new User();
        try {
            User.Role role = User.Role.valueOf(resultSet.getString(DBColumns.USER_ROLE).toUpperCase());
            switch (role) {
                case LIBRARIAN:
                    user = new Librarian();
                    ((Librarian) user).setNumberPhone((resultSet.getString(DBColumns.LIBRARIAN_NUMBER_PHONE)));
                    break;
                case READER:
                    user = new Reader();
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
            throw new DaoException();
        }
        return user;
    }
}
