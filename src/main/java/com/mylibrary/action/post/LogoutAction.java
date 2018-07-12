package com.mylibrary.action.post;

import com.mylibrary.action.Paths;
import com.mylibrary.action.Action;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutAction implements Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String resultPage = Paths.SHOW_START_PAGE;
        req.getSession().invalidate();
        return resultPage;
    }
}
