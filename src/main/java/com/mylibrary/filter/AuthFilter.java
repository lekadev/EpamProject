package com.mylibrary.filter;

import java.util.List;
import javax.servlet.*;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class AuthFilter implements Filter {

    private List<String> allowedURLs;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        allowedURLs = new ArrayList<>();
        allowedURLs.add("/login");
        allowedURLs.add("/new-reader");
        allowedURLs.add("/change-lang");
        allowedURLs.add("/register");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        if(session.getAttribute("user") == null && !allowedURLs.contains(req.getPathInfo())) {
            request.getRequestDispatcher("/index.jsp").forward(req, resp);
            return;
        }
        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {

    }
}
