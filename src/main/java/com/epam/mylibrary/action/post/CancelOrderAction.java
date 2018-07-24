package com.epam.mylibrary.action.post;

import com.epam.mylibrary.action.Action;
import com.epam.mylibrary.action.Parameters;
import com.epam.mylibrary.action.Paths;
import com.epam.mylibrary.action.*;
import com.epam.mylibrary.dao.OrderDao;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.epam.mylibrary.dao.exception.DaoException;
import com.epam.mylibrary.action.exception.ActionException;

public class CancelOrderAction implements Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        String idOrderString = req.getParameter(Parameters.ORDER_ID);
        if (idOrderString == null) {
            throw new ActionException();
        }
        int idOrder = Integer.parseInt(idOrderString);
        OrderDao orderDao = new OrderDao();
        try {
            orderDao.deleteById(idOrder);
        } catch (DaoException e) {
            throw new ActionException();
        }
        return Paths.REDIRECT_PROFILE;
    }
}
