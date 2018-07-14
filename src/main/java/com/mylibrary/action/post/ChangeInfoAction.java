package com.mylibrary.action.post;

import com.mylibrary.action.*;
import com.mylibrary.model.User;
import com.mylibrary.dao.UserDao;
import com.mylibrary.model.Librarian;
import com.mylibrary.db.ConnectionPool;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChangeInfoAction implements Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String resultPage = Paths.SHOW_PROFILE_EDIT_FORM;
        String nameFirst = req.getParameter(Parameters.USER_NAME);
        String nameLast = req.getParameter(Parameters.USER_SURNAME);
        String numberPhone = req.getParameter(Parameters.USER_PHONE);
        User user = (User) req.getSession().getAttribute(Attributes.USER);
        boolean fieldsValid = InputValidator.validateText(nameFirst) && InputValidator.validateText(nameLast);
        if(user.getRole() == User.Role.LIBRARIAN) {
            fieldsValid = fieldsValid && InputValidator.validateNumber(numberPhone);
        }
        if(!fieldsValid) {
            req.getSession().setAttribute(Attributes.PROFILE_UPDATE_MESSAGE, ErrorMessages.TEXT_INPUT_ERROR);
            return resultPage;
        }
        boolean isUpdated = !user.getNameFirst().equals(nameFirst)
                || !user.getNameLast().equals(nameLast);
        if(user.getRole() == User.Role.LIBRARIAN) {
            isUpdated = isUpdated || !((Librarian)user).getNumberPhone().equals(numberPhone);
        }
        if (isUpdated) {
            user.setNameFirst(nameFirst);
            user.setNameLast(nameLast);
            if (user.getRole() == User.Role.LIBRARIAN) {
                ((Librarian) user).setNumberPhone(numberPhone);
            }
            ConnectionPool pool = ConnectionPool.getInstance();
            int rowsUpdated = new UserDao(pool).update(user);
            if(rowsUpdated != 0) {
                req.getSession().setAttribute(Attributes.PROFILE_UPDATE_MESSAGE, ErrorMessages.PROFILE_UPDATE_SUCCESS_MESSAGE);
            }
        }
        return resultPage;
    }
}
