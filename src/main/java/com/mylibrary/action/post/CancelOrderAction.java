package com.mylibrary.action.post;

import java.util.List;
import java.util.Locale;

import com.mylibrary.dao.AuthorDao;
import com.mylibrary.model.*;
import com.mylibrary.dao.BookDao;
import com.mylibrary.dao.OrderDao;
import com.mylibrary.action.Paths;
import com.mylibrary.action.Action;
import com.mylibrary.db.ConnectionPool;
import com.mylibrary.action.Attributes;
import com.mylibrary.action.Parameters;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CancelOrderAction implements Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String resultPage = null;
        String idOrderString = req.getParameter(Parameters.ORDER_ID);
        if(idOrderString != null) {
            ConnectionPool pool = ConnectionPool.getInstance();
            int idOrder = Integer.parseInt(idOrderString);
            int result = new OrderDao(pool).deleteById(idOrder);
            if(result != 0) {
                User user = (User) req.getSession().getAttribute(Attributes.USER);
                String language = (String) req.getSession().getAttribute(Attributes.LANGUAGE);
                List<Order> orders = new OrderDao(pool).findOrdersOfUser(user, new Locale(language));
                for(Order order : orders) {
                    int idBook = order.getBook().getId();
                    Book book = new BookDao(pool).findById(idBook);
                    List<Author> authors = new AuthorDao(pool).findAuthorsOfBook(idBook);
                    book.setAuthors(authors);
                    order.setBook(book);
                    order.setUser(user);
                }
                req.getSession().setAttribute(Attributes.ORDERS, orders);
                resultPage = Paths.SHOW_PROFILE;
            }
        }
        return resultPage;
    }
}
