package com.epam.mylibrary.dao;

import java.sql.*;
import java.util.Map;
import java.util.Locale;
import java.util.HashMap;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import com.epam.mylibrary.db.DBColumns;
import com.epam.mylibrary.db.ConnectionPool;

public class LabelsDao {

    private final static Logger logger = Logger.getLogger(LabelsDao.class);

    private final static String SELECT_ALL = "SELECT * FROM library.label JOIN locale USING(id_locale) WHERE name_locale=?";

    private ConnectionPool pool = ConnectionPool.getInstance();

    public LabelsDao() { }

    public Map<String, String> initLabelData(Locale locale) {
        Map<String, String> labels = new HashMap<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.takeConnection();
            statement = connection.prepareStatement(SELECT_ALL);
            statement.setString(1, locale.getLanguage());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String label = resultSet.getString(DBColumns.LABEL_NAME);
                String text = resultSet.getString(DBColumns.LABEL_TEXT);
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
