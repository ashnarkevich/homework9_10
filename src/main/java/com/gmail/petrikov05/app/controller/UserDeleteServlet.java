package com.gmail.petrikov05.app.controller;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gmail.petrikov05.app.service.UserService;
import com.gmail.petrikov05.app.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserDeleteServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private static final String PAGES = "/WEB-INF/pages";
    private UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext servletContext = getServletContext();
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(PAGES + "/userDelete.jsp");
        try {
            String idStr = req.getParameter("id");
            Long id = Long.parseLong(idStr);
            Integer affectedRows = userService.delete(id);
            req.setAttribute("affectedRows", affectedRows);
            requestDispatcher.forward(req, resp);
        } catch (NumberFormatException e) {
            logger.info(e.getMessage(), e);
            req.setAttribute("Exception", "No correct value");
            requestDispatcher.forward(req, resp);
        }
    }

}
