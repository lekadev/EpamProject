package com.mylibrary.service;

import java.sql.*;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import com.mylibrary.dao.UserDao;
import com.mylibrary.model.Reader;
import com.mylibrary.db.ConnectionPool;
import com.mylibrary.dao.exception.DaoException;
import com.mylibrary.service.exception.ServiceException;

public class UserService {

    private ConnectionPool pool;
    private final static Logger logger = Logger.getLogger(UserService.class);

    public UserService() {
        this.pool = ConnectionPool.getInstance();
    }

    public void createReader(Reader reader) throws ServiceException {
        Connection connection = null;
        int idUser;
        try {
            connection = pool.takeConnection();
            connection.setAutoCommit(false);
            UserDao userDao = new UserDao(connection);
            idUser = userDao.createUser(reader);
            reader.setId(idUser);
            userDao.createReader(reader);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException |DaoException e1) {
            logger.log(Level.ERROR, "Unable to execute transaction of saving new user", e1);
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
    }
}
