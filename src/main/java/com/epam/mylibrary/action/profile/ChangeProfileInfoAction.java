package com.epam.mylibrary.action.profile;

import com.epam.mylibrary.dao.UserDao;
import com.epam.mylibrary.entity.User;
import com.epam.mylibrary.action.Action;
import com.epam.mylibrary.constants.Const;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.epam.mylibrary.dao.exception.DaoException;
import com.epam.mylibrary.action.exception.ActionException;
import static com.epam.mylibrary.validator.FormValidator.isEditProfileFormValid;

public class ChangeProfileInfoAction implements Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        String nameFirstNew = req.getParameter(Const.PARAM_NAME);
        String nameLastNew = req.getParameter(Const.PARAM_SURNAME);
        User user = (User) req.getSession().getAttribute(Const.USER);
        if (!isEditProfileFormValid(req)) {
            return Const.REDIRECT_PROFILE_EDIT_FORM;
        }
        boolean isUpdated = !user.getNameFirst().equals(nameFirstNew)
                || !user.getNameLast().equals(nameLastNew);
        if (!isUpdated) {
            return Const.REDIRECT_PROFILE_EDIT_FORM;
        }
        user.setNameFirst(nameFirstNew);
        user.setNameLast(nameLastNew);
        UserDao userDao = new UserDao();
        try {
            userDao.update(user);
        } catch (DaoException e) {
            throw new ActionException();
        }
        req.getSession().setAttribute(Const.PROFILE_UPDATE_MESSAGE, Const.UPDATE_SUCCESS);
        return Const.REDIRECT_PROFILE_EDIT_FORM;
    }
}
