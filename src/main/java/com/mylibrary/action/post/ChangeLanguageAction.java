package com.mylibrary.action.post;

import java.util.Map;
import java.util.List;
import java.util.Locale;
import com.mylibrary.model.*;
import com.mylibrary.dao.LabelDao;
import com.mylibrary.dao.OrderDao;
import com.mylibrary.action.Paths;
import com.mylibrary.action.Action;
import com.mylibrary.action.Attributes;
import com.mylibrary.db.ConnectionPool;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChangeLanguageAction implements Action {

    private ConnectionPool pool;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String resultPage = Paths.SHOW_PROFILE_EDIT_FORM;
        String languageNew = req.getParameter("language");
        String languageOld = (String) req.getSession().getAttribute(Attributes.LANGUAGE);
        if(languageNew != null && !languageNew.equals(languageOld)) {
            req.getSession().setAttribute(Attributes.LANGUAGE, languageNew);
            pool = ConnectionPool.getInstance();
            Map<String, String> labels = new LabelDao(pool).initLabelData(new Locale(languageNew));
            req.getSession().setAttribute(Attributes.LABELS, labels);
            User user = (User) req.getSession().getAttribute(Attributes.USER);
            if(user == null) {
                return Paths.SHOW_START_PAGE;
            }
            renewSessionData(req);
        }
        return resultPage;
    }

    private void renewSessionData(HttpServletRequest req) {
        Map<Integer, Book> catalogue = (Map<Integer, Book>)req.getServletContext().getAttribute(Attributes.CATALOGUE);
        Locale locale = new Locale((String) req.getSession().getAttribute(Attributes.LANGUAGE));
        User user = (User) req.getSession().getAttribute(Attributes.USER);
        if(user.getRole() == User.Role.LIBRARIAN) {
            List<Order> orders = new OrderDao(pool).findAllOrders(locale);
            for(Order order : orders) {
                int idBook = order.getBook().getId();
                Book book = catalogue.get(idBook);
                order.setBook(book);
                order.setUser(user);
            }
            req.getSession().setAttribute(Attributes.ORDERS, orders);
        } else if(user.getRole() == User.Role.READER) {
            List<Order> orders = new OrderDao(pool).findOrdersOfUser(user, locale);
            for(Order order : orders) {
                int idBook = order.getBook().getId();
                Book book = catalogue.get(idBook);
                order.setBook(book);
                order.setUser(user);
            }
            req.getSession().setAttribute(Attributes.ORDERS, orders);
        }
    }
}