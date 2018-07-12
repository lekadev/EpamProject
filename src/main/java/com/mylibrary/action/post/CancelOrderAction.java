package com.mylibrary.action.post;

import java.util.Map;
import java.util.List;
import java.util.Locale;
import com.mylibrary.model.*;
import com.mylibrary.dao.OrderDao;
import com.mylibrary.action.Paths;
import com.mylibrary.action.Action;
import com.mylibrary.action.Attributes;
import com.mylibrary.db.ConnectionPool;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CancelOrderAction implements Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String resultPage = null;
        String idOrderString = req.getParameter("idOrder");
        if(idOrderString != null) {
            ConnectionPool pool = ConnectionPool.getInstance();
            int idOrder = Integer.parseInt(idOrderString);
            int result = new OrderDao(pool).deleteByOrderId(idOrder);
            if(result != 0) {
                User user = (User) req.getSession().getAttribute(Attributes.USER);
                String language = (String) req.getSession().getAttribute(Attributes.LANGUAGE);
                List<Order> orders = new OrderDao(pool).findOrdersOfUser(user, new Locale(language));
                Map<Integer, Book> catalogue = (Map<Integer, Book>) req.getServletContext().getAttribute(Attributes.CATALOGUE);
                for(Order order : orders) {
                    order.setUser(user);
                    int idBook = order.getBook().getId();
                    Book book = catalogue.get(idBook);
                    order.setBook(book);
                }
                req.getSession().setAttribute(Attributes.ORDERS, orders);
                resultPage = Paths.SHOW_PROFILE;
            }
        }
        return resultPage;
    }
}
