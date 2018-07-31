package com.epam.mylibrary.action.profile;

import com.epam.mylibrary.action.Action;
import com.epam.mylibrary.constants.Const;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutAction implements Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().invalidate();
        return Const.REDIRECT_START_PAGE;
    }
}
