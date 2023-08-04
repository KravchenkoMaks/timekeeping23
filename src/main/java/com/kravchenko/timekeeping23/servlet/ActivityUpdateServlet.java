package com.kravchenko.timekeeping23.servlet;

import com.kravchenko.timekeeping23.dto.ReadActivityDto;
import com.kravchenko.timekeeping23.dto.UpdateActivityDto;
import com.kravchenko.timekeeping23.exception.DBException;
import com.kravchenko.timekeeping23.exception.ValidationException;
import com.kravchenko.timekeeping23.service.ActivityService;
import com.kravchenko.timekeeping23.util.DateAndTimeHelper;
import com.kravchenko.timekeeping23.util.IdHelper;
import com.kravchenko.timekeeping23.util.JspHelper;
import com.kravchenko.timekeeping23.util.UrlPath;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.IOException;

@WebServlet(UrlPath.ACTIVITY_UPDATE)
public class ActivityUpdateServlet extends HttpServlet {

    private final ActivityService activityService = ActivityService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("page", "UpdateActivity");
        try {
            activityService.findById(IdHelper.createId(req))
                    .ifPresent(activity-> onUserSuccess(activity, req, resp));
        } catch (Exception ex) {
            req.setAttribute("ex", ex);
        }
        req.getRequestDispatcher(JspHelper.ERROR).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var id = req.getParameter("id");
        var create = DateAndTimeHelper.createDate(req.getParameter("create"));

        var updateActivityDto = UpdateActivityDto.builder()
                .id(id)
                .activityName(req.getParameter("name"))
                .category(req.getParameter("category"))
                .createDate(create)
                .doneDate(DateAndTimeHelper.doneDate(create, req.getParameter("done")))
                .effortHrs(DateAndTimeHelper.effort(req.getParameter("effort")))
                .state(req.getParameter("state"))
                .description(req.getParameter("description"))
                .build();
        System.out.println();

        try {
            activityService.update(updateActivityDto);
            resp.sendRedirect(UrlPath.ACTIVITIES);
        } catch (ValidationException ex) {
            req.setAttribute("errors", ex.getErrors());
            req.setAttribute("id", id);
            doGet(req, resp);
        } catch (DBException ex) {
            req.setAttribute("ex", ex);
            req.getRequestDispatcher(JspHelper.ERROR).forward(req, resp);
        }
    }

    @SneakyThrows
    private void onUserSuccess(ReadActivityDto activity, HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute("activity", activity);
        req.getRequestDispatcher(JspHelper.getPath("activity_update")).forward(req, resp);
    }
}
