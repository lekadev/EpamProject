package com.mylibrary.action.get;

import com.mylibrary.action.Paths;
import com.mylibrary.action.Action;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowCatalogueAction implements Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String resultPage = Paths.CATALOGUE_PAGE;
        return resultPage;
    }
}
