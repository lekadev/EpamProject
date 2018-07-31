package com.epam.mylibrary.action.profile;

import com.epam.mylibrary.dao.UserDao;
import com.epam.mylibrary.entity.User;
import com.epam.mylibrary.action.Action;
import com.epam.mylibrary.constants.Const;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.epam.mylibrary.dao.exception.DaoException;
import com.epam.mylibrary.action.exception.ActionException;

public class LoginAction implements Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        String email = req.getParameter(Const.PARAM_EMAIL.toLowerCase());
        String password = req.getParameter(Const.PARAM_PASSWORD);
        User user;
        try {
            user = new UserDao().findByEmailAndPassword(email, String.valueOf(password.hashCode()));
        } catch (DaoException e) {
            throw new ActionException();
        }
        if(user == null ) {
            req.getSession().setAttribute(Const.LOGIN_MESSAGE, Const.LOGIN_ERROR);
            return Const.REDIRECT_START_PAGE;
        }
        req.getSession().setAttribute(Const.USER, user);
        req.getSession().setAttribute(Const.ROLE, user.getRole());
        req.getSession().setAttribute(Const.PASSWORD, password);
        return Const.REDIRECT_PROFILE;
    }
}