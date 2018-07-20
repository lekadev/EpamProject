package com.mylibrary.action.get;

import java.util.List;
import com.mylibrary.model.User;
import com.mylibrary.model.Order;
import com.mylibrary.action.Paths;
import com.mylibrary.action.Action;
import com.mylibrary.action.Attributes;
import com.mylibrary.db.ConnectionPool;
import com.mylibrary.service.OrderService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mylibrary.action.exception.ActionException;
import com.mylibrary.service.exception.ServiceException;

public class ShowProfileAction implements Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        String profilePage = null;
        User user = (User) req.getSession().getAttribute(Attributes.USER);
        ConnectionPool pool = ConnectionPool.getInstance();
        OrderService orderService = new OrderService(pool);
        List<Order> orders = null;
        try {
            switch (user.getRole()) {
                case LIBRARIAN:
                    orders = orderService.findAllOrders();
                    profilePage = Paths.PAGE_LIBRARIAN_PROFILE;
                    break;
                case READER:
                    orders = orderService.findOrdersOfUser(user);
                    profilePage = Paths.PAGE_READER_PROFILE;
                    break;
                case GUEST: profilePage = Paths.PAGE_START;
                    break;
            }
        } catch (ServiceException e) {
            throw new ActionException();
        }
        req.getSession().setAttribute(Attributes.ORDERS, orders);
        return profilePage;
    }
}
