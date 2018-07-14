package com.mylibrary.action.post;

import java.util.Map;
import java.util.List;
import java.util.Locale;
import java.util.ArrayList;
import com.mylibrary.model.*;
import com.mylibrary.dao.BookDao;
import com.mylibrary.dao.OrderDao;
import com.mylibrary.dao.AuthorDao;
import com.mylibrary.action.Paths;
import com.mylibrary.action.Action;
import com.mylibrary.action.Attributes;
import com.mylibrary.action.Parameters;
import com.mylibrary.db.ConnectionPool;
import com.mylibrary.service.LabelsService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChangeLanguageAction implements Action {

    private ConnectionPool pool;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String resultPage = Paths.SHOW_PROFILE_EDIT_FORM;
        String languageNew = req.getParameter(Parameters.LANGUAGE);
        String languageOld = (String) req.getSession().getAttribute(Attributes.LANGUAGE);
        if(languageNew != null && !languageNew.equals(languageOld)) {
            req.getSession().setAttribute(Attributes.LANGUAGE, languageNew);
            pool = ConnectionPool.getInstance();
            Map<String, String> labels = new LabelsService(pool).initLabelData(new Locale(languageNew));
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
        Locale locale = new Locale((String) req.getSession().getAttribute(Attributes.LANGUAGE));
        User user = (User) req.getSession().getAttribute(Attributes.USER);
        List<Order> orders = new ArrayList<>();
        if(user.getRole() == User.Role.LIBRARIAN) {
            orders = new OrderDao(pool).findAllOrders(locale);
        } else if(user.getRole() == User.Role.READER) {
            orders = new OrderDao(pool).findOrdersOfUser(user, locale);
        }
        for(Order order : orders) {
            int idBook = order.getBook().getId();
            Book book = new BookDao(pool).findById(idBook);
            List<Author> authors = new AuthorDao(pool).findAuthorsOfBook(idBook);
            book.setAuthors(authors);
            order.setBook(book);
            order.setUser(user);
        }
        req.getSession().setAttribute(Attributes.ORDERS, orders);
    }
}