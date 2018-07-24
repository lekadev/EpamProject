package com.epam.mylibrary.action.get;

import java.util.List;
import com.epam.mylibrary.entity.User;
import com.epam.mylibrary.entity.Order;
import com.epam.mylibrary.action.Paths;
import com.epam.mylibrary.action.Action;
import com.epam.mylibrary.action.Attributes;
import com.epam.mylibrary.service.OrderService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.epam.mylibrary.action.exception.ActionException;
import com.epam.mylibrary.service.exception.ServiceException;

public class ShowProfileAction implements Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        String profilePage = null;
        User user = (User) req.getSession().getAttribute(Attributes.USER);
        OrderService orderService = new OrderService();
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
                case GUEST:
                    profilePage = Paths.PAGE_START;
                    break;
            }
        } catch (ServiceException e) {
            throw new ActionException();
        }
        req.getSession().setAttribute(Attributes.ORDERS, orders);
        return profilePage;
    }
}
