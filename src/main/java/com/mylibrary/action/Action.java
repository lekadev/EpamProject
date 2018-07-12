package com.mylibrary.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Action {
    String execute(HttpServletRequest req, HttpServletResponse resp);
}
