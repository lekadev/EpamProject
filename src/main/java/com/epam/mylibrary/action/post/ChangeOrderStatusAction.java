package com.epam.mylibrary.action.post;

import com.epam.mylibrary.action.*;
import com.epam.mylibrary.entity.Order;
import com.epam.mylibrary.dao.OrderDao;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.epam.mylibrary.dao.exception.DaoException;
import com.epam.mylibrary.action.exception.ActionException;

public class ChangeOrderStatusAction implements Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        String idParameter = req.getParameter(Parameters.ORDER_ID);
        String statusParameter = req.getParameter(Parameters.ORDER_STATUS);
        if (idParameter == null || statusParameter == null) {
            throw new ActionException();
        }
        int idOrder = Integer.parseInt(idParameter);
        Order.OrderStatus status = Order.OrderStatus.valueOf(statusParameter);
        OrderDao orderDao = new OrderDao();
        try {
            orderDao.changeStatus(idOrder, status);
        } catch (DaoException e) {
            throw new ActionException();
        }
        return Paths.REDIRECT_PROFILE;
    }
}
