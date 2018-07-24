package com.epam.mylibrary.action.post;

import java.util.Map;
import java.util.Locale;
import com.epam.mylibrary.dao.LabelsDao;
import com.epam.mylibrary.action.Paths;
import com.epam.mylibrary.action.Action;
import com.epam.mylibrary.action.Attributes;
import com.epam.mylibrary.action.Parameters;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChangeLanguageAction implements Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String languageNew = req.getParameter(Parameters.LANGUAGE);
        String languageOld = (String) req.getSession().getAttribute(Attributes.LANGUAGE);
        if (languageNew != null && !languageNew.equals(languageOld)) {
            req.getSession().setAttribute(Attributes.LANGUAGE, languageNew);
            Map<String, String> labels = new LabelsDao().initLabelData(new Locale(languageNew));
            req.getSession().setAttribute(Attributes.LABELS, labels);
        }
        return Paths.REDIRECT_PROFILE_EDIT_FORM;
    }

}