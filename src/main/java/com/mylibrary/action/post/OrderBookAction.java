package com.mylibrary.action.post;

import java.util.List;
import java.util.Locale;
import com.mylibrary.dao.*;
import com.mylibrary.model.*;
import com.mylibrary.action.Paths;
import com.mylibrary.action.Action;
import com.mylibrary.action.Attributes;
import com.mylibrary.action.Parameters;
import com.mylibrary.db.ConnectionPool;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OrderBookAction implements Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String resultPage = null;
        String idBookString = req.getParameter(Parameters.BOOK_ID);
        if(idBookString != null) {
            int idOrderedBook = Integer.parseInt(idBookString);
            Book orderedBook = new Book();
            orderedBook.setId(idOrderedBook);
            User user = (User)req.getSession().getAttribute(Attributes.USER);
            Order newOrder = new Order();
            newOrder.setBook(orderedBook);
            newOrder.setUser(user);
            newOrder.setStatus(Order.OrderStatus.PENDING);
            ConnectionPool pool = ConnectionPool.getInstance();
            int idOrder = new OrderDao(pool).create(newOrder);
            if(idOrder != 0) {
                Locale locale = new Locale((String) req.getSession().getAttribute(Attributes.LANGUAGE));
                List<Order> orders = new OrderDao(pool).findOrdersOfUser(user, locale);
                for(Order order : orders) {
                    order.setUser(user);
                    int idBook = order.getBook().getId();
                    Book book = new BookDao(pool).findById(idBook);
                    List<Author> authors = new AuthorDao(pool).findAuthorsOfBook(idBook);
                    book.setAuthors(authors);
                    order.setBook(book);
                }
                req.getSession().setAttribute(Attributes.ORDERS,orders);
                resultPage = Paths.SHOW_PROFILE;
            }
        }
        return resultPage;
    }
}
