package com.kravchenko.timekeeping23.servlet;

import com.kravchenko.timekeeping23.service.UserService;
import com.kravchenko.timekeeping23.util.JspHelper;
import com.kravchenko.timekeeping23.util.UrlPath;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(UrlPath.USERS)
public class UsersServlet extends HttpServlet {
    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("page", "Users");
        String address = JspHelper.ERROR;
        try {
            req.getSession().setAttribute("users", userService.findAll());
            address = JspHelper.getPath("list_user");
        } catch (Exception ex) {
            req.setAttribute("ex", ex);
        }
        req.getRequestDispatcher(address).forward(req, resp);
    }
}
