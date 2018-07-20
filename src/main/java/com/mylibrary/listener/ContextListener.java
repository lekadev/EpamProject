package com.mylibrary.listener;

import javax.servlet.*;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import com.mylibrary.model.Order;
import com.mylibrary.db.ConnectionPool;
import com.mylibrary.action.Attributes;

public class ContextListener implements ServletContextListener {

    private final static Logger logger = Logger.getLogger(ContextListener.class);

    private ConnectionPool pool;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        logger.log(Level.INFO, "The app was initialized");
        pool = ConnectionPool.getInstance();
        ServletContext context = event.getServletContext();
        Order.OrderStatus[] allStatuses = Order.OrderStatus.values();
        context.setAttribute(Attributes.ALL_STATUSES, allStatuses);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        pool.dispose();
    }
}
