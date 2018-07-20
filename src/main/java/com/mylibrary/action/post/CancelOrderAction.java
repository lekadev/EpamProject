package com.mylibrary.action.post;

import com.mylibrary.action.*;
import com.mylibrary.dao.OrderDao;
import com.mylibrary.db.ConnectionPool;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mylibrary.dao.exception.DaoException;
import com.mylibrary.action.exception.ActionException;

public class CancelOrderAction implements Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        String idOrderString = req.getParameter(Parameters.ORDER_ID);
        if(idOrderString == null) {
            throw new ActionException();
        }
        int idOrder = Integer.parseInt(idOrderString);
        ConnectionPool pool = ConnectionPool.getInstance();
        OrderDao orderDao = new OrderDao(pool);
        try {
            orderDao.deleteById(idOrder);
        } catch (DaoException e) {
            throw new ActionException();
        }
        return Paths.REDIRECT_PROFILE;
    }
}
