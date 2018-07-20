package com.mylibrary.action.post;

import com.mylibrary.action.*;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import com.mylibrary.entity.User;
import com.mylibrary.dao.UserDao;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mylibrary.validator.InputValidator;
import com.mylibrary.dao.exception.DaoException;
import com.mylibrary.action.exception.ActionException;

public class ChangePasswordAction implements Action {

    private final static Logger logger = Logger.getLogger(ChangePasswordAction.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        String password = req.getParameter(Parameters.USER_PASSWORD);
        String passwordRepeated = req.getParameter(Parameters.USER_PASSWORD_REPEATED);
        boolean passwordValid = InputValidator.isPasswordValid(password) && InputValidator.isPasswordValid(passwordRepeated);
        if (!passwordValid) {
            req.getSession().setAttribute(Attributes.PASSWORD_UPDATE_MESSAGE, ErrorMessages.PASSWORD_PATTERN_ERROR);
            return Paths.REDIRECT_PROFILE_EDIT_FORM;
        }
        boolean passwordsMatch = password.equals(passwordRepeated);
        if (!passwordsMatch) {
            req.getSession().setAttribute(Attributes.PASSWORD_UPDATE_MESSAGE, ErrorMessages.PASSWORD_MATCH_ERROR);
            return Paths.REDIRECT_PROFILE_EDIT_FORM;
        }
        User user = (User) req.getSession().getAttribute(Attributes.USER);
        boolean isUpdated = !user.getPassword().equals(String.valueOf(password.hashCode()));
        if (!isUpdated) {
            return Paths.REDIRECT_PROFILE_EDIT_FORM;
        }
        user.setPassword(String.valueOf(password.hashCode()));
        UserDao userDao = new UserDao();
        try {
            userDao.changePassword(user);
        } catch (DaoException e) {
            throw new ActionException();
        }
        logger.log(Level.INFO, "Password was changed for: " + user.toString());
        req.getSession().setAttribute(Attributes.PASSWORD_UPDATE_MESSAGE, ErrorMessages.PASSWORD_UPDATE_SUCCESS);
        req.getSession().removeAttribute(Attributes.USER);
        return Paths.REDIRECT_START_PAGE;
    }
}
