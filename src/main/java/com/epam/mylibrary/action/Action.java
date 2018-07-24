package com.epam.mylibrary.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.epam.mylibrary.action.exception.ActionException;

public interface Action {
    String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException;
}
