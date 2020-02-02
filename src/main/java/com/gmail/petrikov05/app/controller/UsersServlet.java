package com.gmail.petrikov05.app.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gmail.petrikov05.app.service.TableService;
import com.gmail.petrikov05.app.service.UserService;
import com.gmail.petrikov05.app.service.impl.TableServiceImpl;
import com.gmail.petrikov05.app.service.impl.UserServiceImpl;
import com.gmail.petrikov05.app.service.model.UserDTO;

public class UsersServlet extends HttpServlet {

    private static final String PAGES = "/WEB-INF/pages";
    private UserService userService = UserServiceImpl.getInstance();
    private TableService tableService = TableServiceImpl.getInstance();

    @Override
    public void init() throws ServletException {
        tableService.dropTable();
        tableService.createTable();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<UserDTO> users = userService.findAll();
        req.setAttribute("users", users);

        ServletContext servletContext = getServletContext();
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(PAGES + "/users.jsp");
        requestDispatcher.forward(req, resp);

    }

}
