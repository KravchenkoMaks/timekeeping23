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

@WebServlet(UrlPath.ACTIVITIES)
public class ActivitiesServlet extends HttpServlet {

    private final ActivityService activityService = ActivityService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("page", "Activities");
        String address = JspHelper.ERROR;
        try {
            req.getSession().setAttribute("activities", activityService.findAll());
            address = JspHelper.getPath("list_activity");
        } catch (Exception ex) {
            req.setAttribute("ex", ex.getMessage());
        }
        req.getRequestDispatcher(address).forward(req, resp);
    }
}
