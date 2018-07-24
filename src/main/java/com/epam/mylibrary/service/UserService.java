package com.epam.mylibrary.service;

import java.sql.*;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import com.epam.mylibrary.dao.UserDao;
import com.epam.mylibrary.entity.Reader;
import com.epam.mylibrary.db.ConnectionPool;
import com.epam.mylibrary.dao.exception.DaoException;
import com.epam.mylibrary.service.exception.ServiceException;

public class UserService {

    private final static Logger logger = Logger.getLogger(UserService.class);
    private ConnectionPool pool  = ConnectionPool.getInstance();

    public UserService() { }

    public void createReader(Reader reader) throws ServiceException {
        Connection connection = null;
        int idUser;
        try {
            connection = pool.takeConnection();
            connection.setAutoCommit(false);
            UserDao userDao = new UserDao(connection);
            idUser = userDao.create(reader);
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
