package com.epam.mylibrary.action.post;

import com.epam.mylibrary.action.Paths;
import com.epam.mylibrary.action.Action;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutAction implements Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().invalidate();
        return Paths.REDIRECT_START_PAGE;
    }
}
