package com.epam.mylibrary.action.order;

import com.epam.mylibrary.entity.Order;
import com.epam.mylibrary.dao.OrderDao;
import com.epam.mylibrary.action.Action;
import com.epam.mylibrary.constants.Const;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.epam.mylibrary.dao.exception.DaoException;
import com.epam.mylibrary.action.exception.ActionException;

public class ChangeOrderStatusAction implements Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        String idParameter = req.getParameter(Const.PARAM_ORDER_ID);
        String statusParameter = req.getParameter(Const.PARAM_STATUS);
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
        return Const.REDIRECT_PROFILE;
    }
}
