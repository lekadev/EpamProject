package com.mylibrary.action.post;

import java.util.List;
import java.util.Locale;
import com.mylibrary.model.User;
import com.mylibrary.model.Book;
import com.mylibrary.model.Order;
import com.mylibrary.model.Author;
import com.mylibrary.dao.UserDao;
import com.mylibrary.dao.BookDao;
import com.mylibrary.dao.OrderDao;
import com.mylibrary.dao.AuthorDao;
import com.mylibrary.action.Paths;
import com.mylibrary.action.Action;
import com.mylibrary.action.Attributes;
import com.mylibrary.action.Parameters;
import com.mylibrary.db.ConnectionPool;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ChangeOrderStatusAction implements Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String resultPage = Paths.SHOW_PROFILE;
        String idParameter = req.getParameter(Parameters.ORDER_ID);
        String statusParameter = req.getParameter(Parameters.ORDER_STATUS);
        if(statusParameter != null) {
            int idOrder = Integer.parseInt(idParameter);
            Order.OrderStatus status = Order.OrderStatus.valueOf(statusParameter);
            ConnectionPool pool = ConnectionPool.getInstance();
            int rowsUpdated = new OrderDao(pool).changeStatus(idOrder, status);
            if(rowsUpdated != 0) {
                Locale locale = new Locale((String) req.getSession().getAttribute(Attributes.LANGUAGE));
                List<Order> orders = new OrderDao(pool).findAllOrders(locale);
                for(Order order : orders) {
                    int idBook = order.getBook().getId();
                    Book book = new BookDao(pool).findById(idBook);
                    List<Author> authors = new AuthorDao(pool).findAuthorsOfBook(idBook);
                    book.setAuthors(authors);
                    order.setBook(book);
                    User user = new UserDao(pool).findById(order.getUser().getId());
                    order.setUser(user);
                }
                req.getSession().setAttribute(Attributes.ORDERS, orders);
            }
        }
        return resultPage;
    }
}
