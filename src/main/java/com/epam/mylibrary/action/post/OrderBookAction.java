package com.epam.mylibrary.action.post;

import com.epam.mylibrary.action.Action;
import com.epam.mylibrary.action.Attributes;
import com.epam.mylibrary.action.Parameters;
import com.epam.mylibrary.action.Paths;
import com.epam.mylibrary.entity.Book;
import com.epam.mylibrary.entity.Order;
import com.epam.mylibrary.entity.User;
import com.epam.mylibrary.entity.*;
import com.epam.mylibrary.action.*;
import com.epam.mylibrary.dao.OrderDao;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.epam.mylibrary.dao.exception.DaoException;
import com.epam.mylibrary.action.exception.ActionException;

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
        OrderDao orderDao = new OrderDao();
        try {
            orderDao.create(order);
        } catch (DaoException e) {
            throw new ActionException();
        }
        return Paths.REDIRECT_PROFILE;
    }
}
