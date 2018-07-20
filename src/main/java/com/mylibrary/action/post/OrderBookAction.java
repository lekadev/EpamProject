package com.mylibrary.action.post;

import com.mylibrary.model.*;
import com.mylibrary.action.*;
import com.mylibrary.dao.OrderDao;
import com.mylibrary.db.ConnectionPool;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mylibrary.dao.exception.DaoException;
import com.mylibrary.action.exception.ActionException;

public class OrderBookAction implements Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        String idBookString = req.getParameter(Parameters.BOOK_ID);
        if(idBookString == null) {
            throw new ActionException();
        }
        int idOrderedBook = Integer.parseInt(idBookString);
        Book orderedBook = new Book();
        orderedBook.setId(idOrderedBook);
        User user = (User)req.getSession().getAttribute(Attributes.USER);
        Order order = new Order();
        order.setBook(orderedBook);
        order.setUser(user);
        order.setStatus(Order.OrderStatus.PENDING);
        ConnectionPool pool = ConnectionPool.getInstance();
        OrderDao orderDao = new OrderDao(pool);
        try {
            orderDao.create(order);
        } catch (DaoException e) {
            throw new ActionException();
        }
        return Paths.REDIRECT_PROFILE;
    }
}
