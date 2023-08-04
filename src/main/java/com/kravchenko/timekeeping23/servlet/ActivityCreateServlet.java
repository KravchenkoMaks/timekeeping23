package com.kravchenko.timekeeping23.servlet;

import com.kravchenko.timekeeping23.dto.CreateActivityDto;
import com.kravchenko.timekeeping23.dto.ReadUserDto;
import com.kravchenko.timekeeping23.entity.ActivityCategory;
import com.kravchenko.timekeeping23.entity.ActivityState;
import com.kravchenko.timekeeping23.exception.DBException;
import com.kravchenko.timekeeping23.exception.ValidationException;
import com.kravchenko.timekeeping23.service.ActivityService;
import com.kravchenko.timekeeping23.service.UserService;
import com.kravchenko.timekeeping23.util.JspHelper;
import com.kravchenko.timekeeping23.util.UrlPath;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(UrlPath.ACTIVITY_CREATE)
public class ActivityCreateServlet extends HttpServlet {

    private final ActivityService activityService = ActivityService.getInstance();
    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("page", "Create Activity");
        try {
            req.getSession().setAttribute("users", userService.findAll());
        } catch (Exception ex) {
            req.setAttribute("ex", ex);
            req.getRequestDispatcher(JspHelper.ERROR).forward(req, resp);
        }
        req.getRequestDispatcher(JspHelper.getPath("activity_create")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var createActivityDto = CreateActivityDto.builder()
                .activityName(req.getParameter("activityName"))
                .category(ActivityCategory.valueOf(req.getParameter("category")))
                .user(ReadUserDto.builder()
                        .email(req.getParameter("email"))
                        .build())
                .state(ActivityState.valueOf(req.getParameter("state")))
                .description(req.getParameter("description"))
                .build();
        System.out.println();
        try {
            activityService.create(createActivityDto);
            resp.sendRedirect(UrlPath.ACTIVITIES);
        }catch (ValidationException ex){
            req.setAttribute("errors", ex.getErrors());
            doGet(req, resp);
        }catch (DBException ex) {
            req.setAttribute("ex", ex);
            req.getRequestDispatcher(JspHelper.ERROR).forward(req, resp);
        }
    }
}
