package com.mylibrary.action.post;

import com.mylibrary.dao.*;
import com.mylibrary.action.*;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import com.mylibrary.model.User;
import com.mylibrary.db.ConnectionPool;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChangePasswordAction implements Action {

    private final static Logger logger = Logger.getLogger(ChangePasswordAction.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String resultPage = Paths.SHOW_PROFILE_EDIT_FORM;
        String password = req.getParameter(Parameters.USER_PASSWORD);
        String passwordRepeated = req.getParameter(Parameters.USER_PASSWORD_REPEATED);
        boolean passwordValid = InputValidator.validatePassword(password) && InputValidator.validatePassword(passwordRepeated);
        if(!passwordValid) {
            req.getSession().setAttribute(Attributes.PASSWORD_UPDATE_MESSAGE, ErrorMessages.PASSWORD_VALID_ERROR);
            return resultPage;
        }
        boolean passwordsMatch = password.equals(passwordRepeated);
        if(!passwordsMatch) {
            req.getSession().setAttribute(Attributes.PASSWORD_UPDATE_MESSAGE, ErrorMessages.PASSWORD_MATCH_ERROR);
            return resultPage;
        }
        User user = (User) req.getSession().getAttribute(Attributes.USER);
        boolean isUpdated = !user.getPassword().equals(String.valueOf(password.hashCode()));
        if (isUpdated) {
            user.setPassword(String.valueOf(password.hashCode()));
            ConnectionPool pool = ConnectionPool.getInstance();
            int rowsUpdated = new UserDao(pool).changePassword(user);
            if(rowsUpdated != 0) {
                req.getSession().setAttribute(Attributes.PASSWORD_UPDATE_MESSAGE, ErrorMessages.PASSWORD_UPDATE_SUCCESS_MESSAGE);
                req.getSession().removeAttribute(Attributes.USER);
                resultPage = Paths.SHOW_START_PAGE;
                logger.log(Level.INFO, "Password was changed for: " + user.toString());
            }
            else {
                req.getSession().setAttribute(Attributes.PASSWORD_UPDATE_MESSAGE, ErrorMessages.PASSWORD_UPDATE_ERROR);
            }
        }
        return resultPage;
    }
}
