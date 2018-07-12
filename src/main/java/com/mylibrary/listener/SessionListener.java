package com.mylibrary.listener;

import java.util.Map;
import java.util.Locale;
import com.mylibrary.dao.LabelDao;
import com.mylibrary.db.ConnectionPool;
import com.mylibrary.action.Attributes;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent event) {
        String defaultLanguage = "en";
        event.getSession().setAttribute("language", defaultLanguage);
        ConnectionPool pool = ConnectionPool.getInstance();
        Map<String, String> labels = new LabelDao(pool).initLabelData(new Locale(defaultLanguage));
        event.getSession().setAttribute(Attributes.LABELS, labels);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {

    }
}
