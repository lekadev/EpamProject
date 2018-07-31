package com.epam.mylibrary.action.common;

import java.util.Map;
import java.util.Locale;
import com.epam.mylibrary.dao.LabelsDao;
import com.epam.mylibrary.action.Action;
import com.epam.mylibrary.constants.Const;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.epam.mylibrary.dao.exception.DaoException;
import com.epam.mylibrary.action.exception.ActionException;

public class ChangeLanguageAction implements Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        String languageNew = req.getParameter(Const.PARAM_LANGUAGE);
        String languageOld = (String) req.getSession().getAttribute(Const.LANGUAGE);
        if (languageNew != null && !languageNew.equals(languageOld)) {
            req.getSession().setAttribute(Const.LANGUAGE, languageNew);
            Map<String, String> labels;
            try {
                labels = new LabelsDao().initLabelData(new Locale(languageNew));
            } catch (DaoException e) {
                throw new ActionException();
            }
            req.getSession().setAttribute(Const.LABELS, labels);
        }
        return Const.REDIRECT_PROFILE_EDIT_FORM;
    }

}