package com.mylibrary.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import com.mylibrary.action.Action;
import com.mylibrary.action.ActionFactory;

public class Controller extends HttpServlet {

    private final static String METHOD_GET = "GET";
    private final static String METHOD_POST = "POST";

    @Override
    public void init() {
        //This method has to be overridden
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        handleRequest(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        handleRequest(req, resp);
    }

    private void handleRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Action action = ActionFactory.defineAction(req);
        String resultPage = action.execute(req, resp);
        if(resultPage == null) {
            req.getRequestDispatcher("/jsp/error.jsp").forward(req, resp);
        } else  {
            if(req.getMethod().equals(METHOD_POST)) {
                resp.sendRedirect(resultPage);
            } else if (req.getMethod().equals(METHOD_GET)){
                req.getRequestDispatcher("/jsp/" + resultPage + ".jsp").forward(req,resp);
            }
        }
    }

    @Override
    public void destroy() {
        //This method has to be overridden
    }
}
