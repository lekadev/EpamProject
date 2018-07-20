package com.mylibrary.action.post;

import com.mylibrary.action.*;
import com.mylibrary.model.User;
import com.mylibrary.dao.UserDao;
import com.mylibrary.model.Librarian;
import com.mylibrary.db.ConnectionPool;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mylibrary.validator.InputValidator;
import com.mylibrary.dao.exception.DaoException;
import com.mylibrary.action.exception.ActionException;

public class ChangeProfileInfoAction implements Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        String nameFirstNew = req.getParameter(Parameters.USER_NAME);
        String nameLastNew = req.getParameter(Parameters.USER_SURNAME);
        String numberPhoneNew = req.getParameter(Parameters.USER_PHONE);
        User user = (User) req.getSession().getAttribute(Attributes.USER);
        boolean fieldsValid = InputValidator.isTextValid(nameFirstNew) && InputValidator.isTextValid(nameLastNew);
        if(user.getRole() == User.Role.LIBRARIAN) {
            fieldsValid = fieldsValid && InputValidator.isNumberValid(numberPhoneNew);
        }
        if(!fieldsValid) {
            req.getSession().setAttribute(Attributes.PROFILE_UPDATE_MESSAGE, ErrorMessages.TEXT_INPUT_ERROR);
            return Paths.REDIRECT_PROFILE_EDIT_FORM;
        }
        boolean isUpdated = !user.getNameFirst().equals(nameFirstNew)
                || !user.getNameLast().equals(nameLastNew);
        if(user.getRole() == User.Role.LIBRARIAN) {
            isUpdated = isUpdated || !((Librarian)user).getNumberPhone().equals(numberPhoneNew);
        }
        if(!isUpdated) {
            return Paths.REDIRECT_PROFILE_EDIT_FORM;
        }
        user.setNameFirst(nameFirstNew);
        user.setNameLast(nameLastNew);
        if (user.getRole() == User.Role.LIBRARIAN) {
            ((Librarian) user).setNumberPhone(numberPhoneNew);
        }
        ConnectionPool pool = ConnectionPool.getInstance();
        UserDao userDao = new UserDao(pool);
        try {
            userDao.update(user);
        } catch (DaoException e) {
            throw new ActionException();
        }
        req.getSession().setAttribute(Attributes.PROFILE_UPDATE_MESSAGE, ErrorMessages.UPDATE_SUCCESS);
        return Paths.REDIRECT_PROFILE_EDIT_FORM;
    }
}
