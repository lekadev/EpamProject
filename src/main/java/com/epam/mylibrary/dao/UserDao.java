package com.epam.mylibrary.dao;

import java.sql.*;
import java.util.List;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import com.epam.mylibrary.entity.User;
import com.epam.mylibrary.entity.UserRole;
import com.epam.mylibrary.constants.Const;
import com.epam.mylibrary.db.ConnectionPool;
import com.epam.mylibrary.dao.exception.DaoException;

public class UserDao extends EntityDao<Integer, User> {

    private final static Logger logger = Logger.getLogger(UserDao.class);

    private final static String SELECT_BY_ID = "SELECT id_user, email, password, role, name_first, name_last, date_registered FROM library.user WHERE id_user=?";
    private final static String SELECT_BY_EMAIL = "SELECT id_user, email, password, role, name_first, name_last, date_registered FROM library.user WHERE email=?";
    private final static String SELECT_BY_EMAIL_AND_PASSWORD = "SELECT id_user, email, password, role, name_first, name_last, date_registered FROM library.user WHERE email=? AND password=?";
    private final static String UPDATE_USER = "UPDATE library.user SET name_first=?, name_last=? WHERE id_user=?";
    private final static String UPDATE_PASSWORD = "UPDATE library.user SET password=? WHERE id_user=?";
    private final static String INSERT_USER = "INSERT INTO library.user (email, password, role, name_first, name_last, date_registered) VALUES (?, ?, ?, ?, ?, ?)";

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
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = retrieveUser(resultSet);
            }
            resultSet.close();
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
        int idUser = 0;
        try (Connection connection = pool.takeConnection();
                PreparedStatement statement = connection.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, String.valueOf(user.getRole()));
            statement.setString(4, user.getNameFirst());
            statement.setString(5, user.getNameLast());
            statement.setDate(6, (Date) user.getDateRegistered());
            statement.executeUpdate();
            ResultSet generatedKey = statement.getGeneratedKeys();
            if (generatedKey.next()) {
                idUser = generatedKey.getInt(1);
            }
            generatedKey.close();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to execute new user insertion", e);
            throw new DaoException();
        }
        return idUser;
    }

    @Override
    public void update(User user) throws DaoException {
        try (Connection connection = pool.takeConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_USER)) {
            statement.setString(1, user.getNameFirst());
            statement.setString(2, user.getNameLast());
            statement.setInt(3, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to update user", e);
            throw new DaoException();
        }
    }

    public boolean isRegistered(String email) throws DaoException {
        boolean isRegistered = true;
        try (Connection connection = pool.takeConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_BY_EMAIL)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                isRegistered = false;
            }
            resultSet.close();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to check user by email", e);
            throw new DaoException();
        }
        return isRegistered;
    }

    public User findByEmailAndPassword(String email, String password) throws DaoException {
        User user = null;
        try (Connection connection = pool.takeConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_BY_EMAIL_AND_PASSWORD)){
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = retrieveUser(resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to find user by email and password", e);
            throw new DaoException();
        }
        return user;
    }

    public void changePassword(User user) throws DaoException {
        try (Connection connection = pool.takeConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_PASSWORD)) {
            statement.setString(1, user.getPassword());
            statement.setInt(2, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to update password", e);
            throw new DaoException();
        }
    }

    private User retrieveUser(ResultSet resultSet) throws DaoException {
        User user = new User();
        try {
            user.setId(resultSet.getInt(Const.USER_ID));
            user.setEmail(resultSet.getString(Const.USER_EMAIL));
            user.setPassword(resultSet.getString(Const.USER_PASSWORD));
            user.setRole(UserRole.valueOf(resultSet.getString(Const.USER_ROLE)));
            user.setNameFirst(resultSet.getString(Const.USER_NAME_FIRST));
            user.setNameLast(resultSet.getString(Const.USER_NAME_LAST));
            user.setDateRegistered(resultSet.getDate(Const.USER_DATE_REGISTERED));
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to parse result set for user", e);
            throw new DaoException();
        }
        return user;
    }
}
