package com.epam.mylibrary.action.profile;

import java.util.List;
import com.epam.mylibrary.entity.User;
import com.epam.mylibrary.entity.Order;
import com.epam.mylibrary.action.Action;
import com.epam.mylibrary.constants.Const;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.epam.mylibrary.service.OrderService;
import com.epam.mylibrary.action.exception.ActionException;
import com.epam.mylibrary.service.exception.ServiceException;

public class ShowProfileAction implements Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        String profilePage = null;
        User user = (User) req.getSession().getAttribute(Const.USER);
        OrderService orderService = new OrderService();
        List<Order> orders = null;
        try {
            switch (user.getRole()) {
                case LIBRARIAN:
                    orders = orderService.findAllOrders();
                    profilePage = Const.FORWARD_LIBRARIAN_PROFILE;
                    break;
                case READER:
                    orders = orderService.findOrdersOfUser(user);
                    profilePage = Const.FORWARD_READER_PROFILE;
                    break;
                case GUEST:
                    profilePage = Const.FORWARD_START;
                    break;
            }
        } catch (ServiceException e) {
            throw new ActionException();
        }
        req.getSession().setAttribute(Const.ORDERS, orders);
        return profilePage;
    }
}
