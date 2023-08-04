package com.kravchenko.timekeeping23.servlet;

import com.kravchenko.timekeeping23.dto.ReadUserDto;
import com.kravchenko.timekeeping23.service.ActivityService;
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

@WebServlet(UrlPath.USER_ACCOUNT)
public class UserAccountServlet extends HttpServlet {
    private final UserService userService = UserService.getInstance();
    private final ActivityService activityService = ActivityService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("page", "Account");
        var id = Integer.valueOf(req.getParameter("id"));
        try {
            req.setAttribute("userActivity", activityService.findAllUserActivity(id));
            userService.findById(id)
                    .ifPresent(user -> onLoginSuccess(user, req, resp));
        } catch (Exception ex) {
            req.setAttribute("ex", ex.getMessage());
        }
        req.getRequestDispatcher(JspHelper.ERROR).forward(req, resp);
    }

    @SneakyThrows
    private void onLoginSuccess(ReadUserDto user, HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute("user", user);
        req.getRequestDispatcher(JspHelper.getPath("user_account")).forward(req, resp);
    }
}
