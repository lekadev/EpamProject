package com.epam.mylibrary.dao;

import java.sql.*;
import java.util.Map;
import java.util.Locale;
import java.util.HashMap;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import com.epam.mylibrary.constants.Const;
import com.epam.mylibrary.db.ConnectionPool;
import com.epam.mylibrary.dao.exception.DaoException;

public class LabelsDao {

    private final static Logger logger = Logger.getLogger(LabelsDao.class);

    private final static String SELECT_ALL = "SELECT id_label, label, text, id_locale FROM library.label JOIN locale USING(id_locale) WHERE name_locale=?";

    private ConnectionPool pool = ConnectionPool.getInstance();

    public LabelsDao() { }

    public Map<String, String> initLabelData(Locale locale) throws DaoException {
        Map<String, String> labels = new HashMap<>();
        try (Connection connection = pool.takeConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_ALL)) {
            statement.setString(1, locale.getLanguage());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String label = resultSet.getString(Const.LABEL_NAME);
                String text = resultSet.getString(Const.LABEL_TEXT);
                labels.put(label, text);
            }
            resultSet.close();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to load labels", e);
            throw new DaoException();
        }
        return labels;
    }
}
