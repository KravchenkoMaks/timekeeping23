package com.kravchenko.timekeeping23.servlet;

import com.kravchenko.timekeeping23.service.ActivityService;
import com.kravchenko.timekeeping23.util.JspHelper;
import com.kravchenko.timekeeping23.util.UrlPath;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(UrlPath.USER_ACTIVITY)
public class UserActivityServlet extends HttpServlet {

    private final ActivityService activityService = ActivityService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("page", "UserActivity");
        String address = JspHelper.ERROR;

        var email = req.getParameter("email");
        var id = Integer.valueOf(req.getParameter("id"));
        try {
            req.setAttribute("userActivity", activityService.findAllUserActivity(id));
            req.setAttribute("email", email);
            req.setAttribute("id", id);
            address = JspHelper.getPath("user_activity");
        } catch (Exception ex) {
            req.setAttribute("ex", ex);
        }
        req.getRequestDispatcher(address).forward(req, resp);
    }


}
