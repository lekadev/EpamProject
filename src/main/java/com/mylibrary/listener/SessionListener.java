package com.mylibrary.listener;

import java.util.Map;
import java.util.Locale;
import com.mylibrary.db.ConnectionPool;
import com.mylibrary.action.Attributes;
import com.mylibrary.service.LabelsService;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {

    private static final String LANGUAGE_EN = "en";

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        String defaultLanguage = LANGUAGE_EN;
        event.getSession().setAttribute(Attributes.LANGUAGE, defaultLanguage);
        ConnectionPool pool = ConnectionPool.getInstance();
        Map<String, String> labels = new LabelsService(pool).initLabelData(new Locale(defaultLanguage));
        event.getSession().setAttribute(Attributes.LABELS, labels);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {}
}
