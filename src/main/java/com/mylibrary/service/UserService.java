package com.mylibrary.service;

import java.sql.*;
import java.util.Calendar;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import com.mylibrary.model.Reader;
import com.mylibrary.db.ConnectionPool;

public class UserService {

    private ConnectionPool pool;
    private final static Logger logger = Logger.getLogger(UserService.class);

    public UserService(ConnectionPool pool) {
        this.pool = pool;
    }

    public Integer createReader(Reader reader){
        String userQuery = "INSERT INTO library.user (email, password, role) VALUES (?, ?, ?)";
        String readerQuery = "INSERT INTO library.reader(name_first, name_last, date_registered, id_user) VALUES (?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement userStatement;
        PreparedStatement readerStatement;
        ResultSet generatedKey;
        int idUser = 0;
        try {
            connection = pool.takeConnection();
            connection.setAutoCommit(false);
            userStatement = connection.prepareStatement(userQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            userStatement.setString(1, reader.getEmail());
            userStatement.setString(2, reader.getPassword());
            userStatement.setString(3, reader.getRole().toString());
            userStatement.executeUpdate();
            generatedKey = userStatement.getGeneratedKeys();
            if(generatedKey.next()) {
                idUser = generatedKey.getInt(1);
                if(idUser != 0) {
                    readerStatement = connection.prepareStatement(readerQuery);
                    readerStatement.setString(1, reader.getNameFirst());
                    readerStatement.setString(2, reader.getNameLast());
                    java.sql.Date currentDate = new Date(Calendar.getInstance().getTimeInMillis());
                    readerStatement.setDate(3, currentDate);
                    readerStatement.setInt(4, idUser);
                    readerStatement.executeUpdate();
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to execute new user insertion transaction", e);
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException e1) {
                logger.log(Level.ERROR, "Unable to roll back user insertion transaction", e);
            }
        } finally {
            pool.closeConnection(connection);
        }
        return idUser;
    }
}
