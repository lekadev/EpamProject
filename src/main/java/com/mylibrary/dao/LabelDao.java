package com.mylibrary.dao;

import java.sql.*;
import java.util.Map;
import java.util.Locale;
import java.util.HashMap;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import com.mylibrary.db.ConnectionPool;

public class LabelDao {

    private ConnectionPool pool;
    private final static Logger logger = Logger.getLogger(LabelDao.class);

    private final String sql = "SELECT * FROM library.label JOIN locale USING(id_locale) WHERE name_locale=?";

    public LabelDao(ConnectionPool pool) {
        this.pool = pool;
    }

    public Map<String, String> initLabelData(Locale locale) {
        Map<String, String> labels = new HashMap<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.takeConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, locale.getLanguage());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String label = resultSet.getString("label");
                String text = resultSet.getString("text");
                labels.put(label, text);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to load labels", e);
        } finally {
            pool.closeConnection(connection, statement, resultSet);
        }
        return labels;
    }
}
