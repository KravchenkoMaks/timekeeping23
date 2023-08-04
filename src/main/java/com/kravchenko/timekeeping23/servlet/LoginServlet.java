package com.kravchenko.timekeeping23.servlet;

import com.kravchenko.timekeeping23.dto.ReadUserDto;
import com.kravchenko.timekeeping23.exception.DBException;
import com.kravchenko.timekeeping23.service.UserService;
import com.kravchenko.timekeeping23.util.JspHelper;
import com.kravchenko.timekeeping23.util.UrlPath;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.IOException;

@WebServlet(UrlPath.LOGIN)
public class LoginServlet extends HttpServlet {

    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("page", "Login");
        req.getRequestDispatcher(JspHelper.getPath("login")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            userService.login(req.getParameter("email"), req.getParameter("password"))
                    .ifPresentOrElse(
                            user -> onLoginSuccess(user, req, resp),
                            () -> onLoginFail(req, resp)
                    );
        } catch (DBException ex) {
            req.setAttribute("ex", ex);
            req.getRequestDispatcher(JspHelper.ERROR).forward(req, resp);
        }
    }

    @SneakyThrows
    private void onLoginFail(HttpServletRequest req, HttpServletResponse resp) {
        resp.sendRedirect(UrlPath.LOGIN +"?error&email=" + req.getParameter("email"));
    }

    @SneakyThrows
    private void onLoginSuccess(ReadUserDto user, HttpServletRequest req, HttpServletResponse resp){
        req.getSession().setAttribute("user", user);
        req.getSession().setAttribute("role", user.getRole());
        resp.sendRedirect(UrlPath.ACTIVITIES);
    }
}
