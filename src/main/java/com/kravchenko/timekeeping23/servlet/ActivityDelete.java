package com.kravchenko.timekeeping23.servlet;

import com.kravchenko.timekeeping23.exception.DBException;
import com.kravchenko.timekeeping23.service.ActivityService;
import com.kravchenko.timekeeping23.util.JspHelper;
import com.kravchenko.timekeeping23.util.UrlPath;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(UrlPath.ACTIVITY_DELETE)
public class ActivityDelete extends HttpServlet {

    private final ActivityService activityService = ActivityService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            activityService.delete(Integer.valueOf(req.getParameter("id")));
                resp.sendRedirect(UrlPath.ACTIVITIES);

        } catch (DBException ex) {
            req.setAttribute("ex", ex);
            req.getRequestDispatcher(JspHelper.ERROR).forward(req, resp);
        }
    }
}
