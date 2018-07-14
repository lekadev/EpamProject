package com.mylibrary.action.post;

import java.util.List;
import java.util.Locale;
import java.util.ArrayList;
import com.mylibrary.dao.*;
import com.mylibrary.model.*;
import com.mylibrary.action.*;
import com.mylibrary.db.ConnectionPool;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginAction implements Action {

    private ConnectionPool pool;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp){
        String resultPage = Paths.SHOW_PROFILE;
        req.getSession().removeAttribute(Attributes.LOGIN_MESSAGE);
        req.getSession().removeAttribute(Attributes.PASSWORD_UPDATE_MESSAGE);
        String email = req.getParameter(Parameters.USER_EMAIL.toLowerCase());
        String password = req.getParameter(Parameters.USER_PASSWORD);
        boolean fieldsValid = InputValidator.validateText(email) && InputValidator.validateText(password);
        if(!fieldsValid) {
            req.getSession().setAttribute(Attributes.LOGIN_MESSAGE, ErrorMessages.TEXT_INPUT_ERROR);
            return Paths.SHOW_START_PAGE;
        }
        pool = ConnectionPool.getInstance();
        User user = new UserDao(pool).findByEmailAndPassword(email, String.valueOf(password.hashCode()));
        if(user == null ) {
            req.getSession().setAttribute(Attributes.LOGIN_MESSAGE, ErrorMessages.LOGIN_VALID_ERROR);
            return Paths.SHOW_START_PAGE;
        }
        setUser(req, user);
        req.getSession().setAttribute(Attributes.USER, user);
        req.getSession().setAttribute(Attributes.PASSWORD, password);
        return resultPage;
    }

    private void setUser(HttpServletRequest req, User user) {
        String language = (String) req.getSession().getAttribute(Attributes.LANGUAGE);
        Locale locale = new Locale(language);
        List<Order> orders = new ArrayList<>();
        if(user.getRole() == User.Role.LIBRARIAN) {
            orders = new OrderDao(pool).findAllOrders(locale);
            for (Order order : orders) {
                int idUser = order.getUser().getId();
                order.setUser(new UserDao(pool).findById(idUser));
            }
        } else if(user.getRole() == User.Role.READER) {
            orders = new OrderDao(pool).findOrdersOfUser(user, locale);
            for(Order order : orders) {
                order.setUser(user);
            }
        }
        for(Order order : orders) {
            int idBook = order.getBook().getId();
            Book book = new BookDao(pool).findById(idBook);
            List<Author> authors = new AuthorDao(pool).findAuthorsOfBook(idBook);
            book.setAuthors(authors);
            order.setBook(book);
        }
        req.getSession().setAttribute(Attributes.ORDERS, orders);
    }
}