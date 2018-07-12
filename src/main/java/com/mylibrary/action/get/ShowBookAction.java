package com.mylibrary.action.get;

import java.util.Map;
import com.mylibrary.model.Book;
import com.mylibrary.action.Paths;
import com.mylibrary.action.Action;
import com.mylibrary.action.Attributes;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowBookAction implements Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String resultPage = null;
        String idParameter = req.getParameter("idBook");
        if(idParameter != null) {
            int idBook = Integer.parseInt(idParameter);
            Map<Integer, Book> catalogue = (Map<Integer, Book>) req.getServletContext().getAttribute(Attributes.CATALOGUE);
            Book book = catalogue.get(idBook);
            req.setAttribute(Attributes.BOOK, book);
            resultPage = Paths.BOOK_INFO_PAGE;
        }
        return resultPage;
    }
}