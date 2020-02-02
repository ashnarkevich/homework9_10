package com.gmail.petrikov05.app.controller;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gmail.petrikov05.app.controller.exception.NoValidParameterException;
import com.gmail.petrikov05.app.service.UserService;
import com.gmail.petrikov05.app.service.impl.UserServiceImpl;
import com.gmail.petrikov05.app.service.model.AddUserDTO;
import com.gmail.petrikov05.app.service.model.UserDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserAddServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private static final String PAGES = "/WEB-INF/pages";
    private UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext servletContext = getServletContext();
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(PAGES + "/userAdd.jsp");
        try {
            AddUserDTO addUserDTO = getAddUser(req);
            validParameterFormat(addUserDTO);
            UserDTO addedUserDTO = userService.add(addUserDTO);
            req.setAttribute("addedUser", addedUserDTO);
            requestDispatcher.forward(req, resp);
        } catch (NoValidParameterException e) {
            logger.info("no valid: " + e.getMessage(), e);
            req.setAttribute("exception", e.getMessage());
            requestDispatcher.forward(req, resp);
        }

    }

    private void validParameterFormat(AddUserDTO addUserDTO) throws NoValidParameterException {
        validUserName(addUserDTO.getUserName());
        validPassword(addUserDTO.getPassword());
        validAge(addUserDTO.getAge());
        validAddress(addUserDTO.getAddress());
        validTelephone(addUserDTO.getTelephone());

    }

    private void validTelephone(String telephone) throws NoValidParameterException {
        int minLength = 1;
        int maxLength = 40;
        if (telephone.length() < minLength || telephone.length() > maxLength) {
            throw new NoValidParameterException("error in telephone format");
        }
    }

    private void validAddress(String address) throws NoValidParameterException {
        int minLength = 1;
        int maxLength = 100;
        if (address.length() < minLength || address.length() > maxLength) {
            throw new NoValidParameterException("error in address format");
        }
    }

    private void validAge(Integer age) throws NoValidParameterException {
        int minAge = 5;
        int maxAge = 120;
        if (age < minAge || age > maxAge) {
            throw new NoValidParameterException("error in age format");
        }
    }

    private void validPassword(String password) throws NoValidParameterException {
        int minLength = 1;
        int maxLength = 40;
        if (password.length() < minLength || password.length() > maxLength) {
            throw new NoValidParameterException("error in password format");
        }
    }

    private void validUserName(String userName) throws NoValidParameterException {
        int minLength = 1;
        int maxLength = 40;
        if (userName.length() < minLength || userName.length() > maxLength) {
            throw new NoValidParameterException("error in name format");
        }
    }

    private AddUserDTO getAddUser(HttpServletRequest req) throws NoValidParameterException {
        String userName = req.getParameter("username");
        String password = req.getParameter("password");
        String active = req.getParameter("active");
        String strAge = req.getParameter("age");
        String address = req.getParameter("address");
        String telephone = req.getParameter("telephone");

        AddUserDTO addUserDTO = new AddUserDTO();
        addUserDTO.setUserName(userName);
        addUserDTO.setPassword(password);
        addUserDTO.setAddress(address);
        addUserDTO.setTelephone(telephone);
        try {
            boolean isActive = Boolean.parseBoolean(active);
            addUserDTO.setIsActive(isActive);
            int age = Integer.parseInt(strAge);
            addUserDTO.setAge(age);
            return addUserDTO;
        } catch (NumberFormatException e) {
            throw new NoValidParameterException("No correct value");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext servletContext = getServletContext();
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(PAGES + "/userAdd.jsp");
        requestDispatcher.forward(req, resp);
    }

}
