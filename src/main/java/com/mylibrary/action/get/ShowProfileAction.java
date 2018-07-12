package com.mylibrary.action.get;

import com.mylibrary.model.User;
import com.mylibrary.action.Paths;
import com.mylibrary.action.Action;
import com.mylibrary.action.Attributes;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowProfileAction implements Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        User user = (User) req.getSession().getAttribute(Attributes.USER);
        User.Role role = user.getRole();
        String profilePage = null;
        switch (role) {
            case LIBRARIAN: profilePage = Paths.LIBRARIAN_PROFILE_PAGE;
                break;
            case READER: profilePage = Paths.READER_PROFILE_PAGE;
                break;
            case GUEST: profilePage = Paths.START_PAGE;
                break;
        }
        return profilePage;
    }
}
