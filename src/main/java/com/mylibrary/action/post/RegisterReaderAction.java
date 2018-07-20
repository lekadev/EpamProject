package com.mylibrary.action.post;

import java.sql.Date;
import java.util.Calendar;
import com.mylibrary.action.*;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import com.mylibrary.model.User;
import com.mylibrary.dao.UserDao;
import com.mylibrary.model.Reader;
import com.mylibrary.service.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mylibrary.validator.InputValidator;
import com.mylibrary.dao.exception.DaoException;
import com.mylibrary.action.exception.ActionException;
import com.mylibrary.service.exception.ServiceException;

public class RegisterReaderAction implements Action {

    private final static Logger logger = Logger.getLogger(RegisterReaderAction.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        String email = req.getParameter(Parameters.USER_EMAIL);
        boolean emailValid = InputValidator.isEmailValid(email);
        if(!emailValid) {
            req.getSession().setAttribute(Attributes.REGISTRATION_MESSAGE, ErrorMessages.EMAIL_INVALID_ERROR);
            return Paths.REDIRECT_READER_FORM;
        }
        boolean isRegistered;
        try {
            isRegistered = new UserDao().isRegistered(email);
        } catch (DaoException e) {
            throw new ActionException();
        }
        if(isRegistered) {
            req.getSession().setAttribute(Attributes.REGISTRATION_MESSAGE, ErrorMessages.EMAIL_EXIST_ERROR);
            return Paths.REDIRECT_READER_FORM;
        }
        String password = req.getParameter(Parameters.USER_PASSWORD);
        String passwordRepeated = req.getParameter(Parameters.USER_PASSWORD_REPEATED);
        boolean passwordValid = InputValidator.isPasswordValid(password);
        if(!passwordValid) {
            req.getSession().setAttribute(Attributes.REGISTRATION_MESSAGE, ErrorMessages.PASSWORD_PATTERN_ERROR);
            return Paths.REDIRECT_READER_FORM;
        }
        boolean passwordsMatch = password.equals(passwordRepeated);
        if(!passwordsMatch) {
            req.getSession().setAttribute(Attributes.REGISTRATION_MESSAGE, ErrorMessages.PASSWORD_MATCH_ERROR);
            return Paths.REDIRECT_READER_FORM;
        }
        String nameFirst = req.getParameter(Parameters.USER_NAME);
        String nameLast = req.getParameter(Parameters.USER_SURNAME);
        boolean fieldsValid = InputValidator.isTextValid(nameFirst) && InputValidator.isTextValid(nameLast);
        if(!fieldsValid) {
            req.getSession().setAttribute(Attributes.REGISTRATION_MESSAGE, ErrorMessages.TEXT_INPUT_ERROR);
            return Paths.REDIRECT_READER_FORM;
        }
        Reader reader = new Reader();
        reader.setEmail(email);
        reader.setPassword(String.valueOf(password.hashCode()));
        reader.setNameFirst(nameFirst);
        reader.setNameLast(nameLast);
        reader.setRole(User.Role.READER);
        java.sql.Date currentDate = new Date(Calendar.getInstance().getTimeInMillis());
        reader.setDateRegistered(currentDate);
        UserService userService = new UserService();
        try {
            userService.createReader(reader);
        } catch (ServiceException e) {
            throw new ActionException();
        }
        req.getSession().setAttribute(Attributes.REGISTRATION_MESSAGE, ErrorMessages.REGISTRATION_SUCCESS);
        logger.log(Level.INFO, "New reader was registered: " + reader.toString());
        return Paths.REDIRECT_START_PAGE;
    }
}
