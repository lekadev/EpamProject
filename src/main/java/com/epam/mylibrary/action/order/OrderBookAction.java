package com.epam.mylibrary.action.order;

import com.epam.mylibrary.entity.*;
import com.epam.mylibrary.dao.OrderDao;
import com.epam.mylibrary.action.Action;
import com.epam.mylibrary.constants.Const;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.epam.mylibrary.dao.exception.DaoException;
import com.epam.mylibrary.action.exception.ActionException;

public class OrderBookAction implements Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        String idBookString = req.getParameter(Const.PARAM_BOOK_ID);
        if(idBookString == null) {
            throw new ActionException();
        }
        int idOrderedBook = Integer.parseInt(idBookString);
        Book orderedBook = new Book();
        orderedBook.setId(idOrderedBook);
        User user = (User)req.getSession().getAttribute(Const.USER);
        Order order = new Order();
        order.setBook(orderedBook);
        order.setUser(user);
        order.setStatus(Order.OrderStatus.PENDING);
        OrderDao orderDao = new OrderDao();
        try {
            orderDao.create(order);
        } catch (DaoException e) {
            throw new ActionException();
        }
        return Const.REDIRECT_PROFILE;
    }
}
