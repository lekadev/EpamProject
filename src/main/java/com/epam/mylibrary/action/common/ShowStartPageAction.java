package com.epam.mylibrary.action.common;

import com.epam.mylibrary.action.Action;
import com.epam.mylibrary.constants.Const;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowStartPageAction implements Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        return Const.FORWARD_START;
    }
}
