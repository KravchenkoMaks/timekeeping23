package com.kravchenko.timekeeping23.servlet;

import com.kravchenko.timekeeping23.dto.ReadActivityDto;
import com.kravchenko.timekeeping23.service.ActivityService;
import com.kravchenko.timekeeping23.util.JspHelper;
import com.kravchenko.timekeeping23.util.UrlPath;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.IOException;

@WebServlet(UrlPath.ACTIVITY_INFO)
public class ActivityInfoServlet extends HttpServlet {

    private final ActivityService activityService = ActivityService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("page", "Activity");
        Integer id = Integer.valueOf(req.getParameter("id"));
        try {
            activityService.findById(id)
                    .ifPresent(activity -> onLoginSuccess(activity, req, resp));
        } catch (Exception ex) {
            req.setAttribute("ex", ex.getMessage());
        }
        req.getRequestDispatcher(JspHelper.ERROR).forward(req, resp);
    }

    @SneakyThrows
    private void onLoginSuccess(ReadActivityDto activity, HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute("activity", activity);
        req.getRequestDispatcher(JspHelper.getPath("activity_info")).forward(req, resp);
    }
}
