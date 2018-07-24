package com.epam.mylibrary.listener;

import java.util.Map;
import java.util.Locale;
import com.epam.mylibrary.dao.LabelsDao;
import com.epam.mylibrary.action.Attributes;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {

    private static final String LANGUAGE_EN = "en";

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        String defaultLanguage = LANGUAGE_EN;
        event.getSession().setAttribute(Attributes.LANGUAGE, defaultLanguage);
        Map<String, String> labels = new LabelsDao().initLabelData(new Locale(defaultLanguage));
        event.getSession().setAttribute(Attributes.LABELS, labels);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {}
}
