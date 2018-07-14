package com.mylibrary.action.post;

import com.mylibrary.action.*;
import com.mylibrary.service.UserService;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import com.mylibrary.model.User;
import com.mylibrary.dao.UserDao;
import com.mylibrary.model.Reader;
import com.mylibrary.db.ConnectionPool;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterReaderAction implements Action {

    private final static Logger logger = Logger.getLogger(RegisterReaderAction.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String resultPage = Paths.SHOW_READER_FORM;
        String email = req.getParameter(Parameters.USER_EMAIL);
        boolean emailValid = InputValidator.validateEmail(email);
        if(!emailValid) {
            req.getSession().setAttribute(Attributes.REGISTRATION_MESSAGE, ErrorMessages.EMAIL_VALID_ERROR);
            return resultPage;
        }
        ConnectionPool pool = ConnectionPool.getInstance();
        boolean isRegistered = new UserDao(pool).isRegistered(email);
        if(isRegistered) {
            req.getSession().setAttribute(Attributes.REGISTRATION_MESSAGE, ErrorMessages.EMAIL_EXIST_ERROR);
            return resultPage;
        }
        String password = req.getParameter(Parameters.USER_PASSWORD);
        String passwordRepeated = req.getParameter(Parameters.USER_PASSWORD_REPEATED);
        boolean passwordsEqual = password.equals(passwordRepeated);
        if(!passwordsEqual) {
            req.getSession().setAttribute(Attributes.REGISTRATION_MESSAGE, ErrorMessages.PASSWORD_MATCH_ERROR);
            return resultPage;
        }
        boolean passwordValid = InputValidator.validatePassword(password);
        if(!passwordValid) {
            req.getSession().setAttribute(Attributes.REGISTRATION_MESSAGE, ErrorMessages.PASSWORD_VALID_ERROR);
            return resultPage;
        }
        String nameFirst = req.getParameter(Parameters.USER_NAME);
        String nameLast = req.getParameter(Parameters.USER_SURNAME);
        boolean nameValid = InputValidator.validateText(nameFirst) && InputValidator.validateText(nameLast);
        if(!nameValid) {
            req.getSession().setAttribute(Attributes.REGISTRATION_MESSAGE, ErrorMessages.TEXT_INPUT_ERROR);
            return resultPage;
        }
        Reader reader = new Reader();
        reader.setEmail(email);
        reader.setPassword(String.valueOf(password.hashCode()));
        reader.setNameFirst(nameFirst);
        reader.setNameLast(nameLast);
        reader.setRole(User.Role.READER);
        int idUser = new UserService(pool).createReader(reader);
        if(idUser != 0) {
            req.getSession().setAttribute(Attributes.REGISTRATION_MESSAGE, ErrorMessages.REGISTRATION_SUCCESS_MESSAGE);
            logger.log(Level.INFO, "New reader was registered: " + reader.toString());
            return Paths.SHOW_START_PAGE;
        } else {
            req.getSession().setAttribute(Attributes.REGISTRATION_MESSAGE, ErrorMessages.REGISTRATION_ERROR);
        }
        return resultPage;
    }
}
