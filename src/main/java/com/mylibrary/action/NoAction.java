package com.mylibrary.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NoAction implements Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        return null;
    }
}
